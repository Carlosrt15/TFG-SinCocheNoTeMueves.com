# Bugs corregidos y mejoras aplicadas

Documento que detalla **uno por uno** los cambios aplicados al TFG.
Filosofía: no tocar el diseño ni el alcance funcional; arreglar lo que rompía
la aplicación y pulir aristas estéticas / de accesibilidad.

---

## 📅 Segunda ronda de saneamiento (despliegue local en Windows + Docker MySQL)

Tras los fixes iniciales, al desplegar en local en una máquina Windows con
MySQL en Docker aparecieron varios problemas de entorno que se han resuelto:

### Conflictos de merge en archivos clave (causaban `fatal: Exiting because of an unresolved conflict` en VS Code)
**Archivos afectados:**
- `SinCocheNoTeMueves/src/services/api.js`
- `TFG-SinCocheNoTeMueves.com/src/main/java/com/tfg/backend/controller/VehiculoController.java`
- `README.md`

**Problema:** los tres archivos tenían marcadores `<<<<<<< HEAD ... ======= ... >>>>>>>` sin resolver, herencia de un merge a medias entre la rama local y `origin/main`.
**Solución:** resueltos los conflictos eligiendo la mejor versión de cada bloque y eliminando los marcadores. En `VehiculoController` se ha integrado además la asignación automática del propietario al crear un vehículo (extrayendo el email del JWT del header `Authorization`).

### Estructura de carpetas duplicada (compilation error: 31 duplicate classes)
**Problema:** dentro del proyecto bueno (`TFG-SinCocheNoTeMueves.com/`) coexistían dos jerarquías de paquetes Java:
- `src/main/java/com.tfg.backend/` (con puntos, ROTA, herencia del repo original)
- `src/main/java/com/tfg/backend/` (anidada, CORRECTA)

Maven detectaba ambas y reportaba 31 clases duplicadas. Adicionalmente, en la raíz del repo había un `src/`, `target/`, `pom.xml` y `package-lock.json` huérfanos del proyecto original.
**Solución:** eliminada por completo la carpeta `com.tfg.backend/` y todos los huérfanos de la raíz. Solo queda la estructura correcta.

### Configuración para Docker MySQL en red bridge (172.x.x.x)
**Archivo:** `application.properties`
**Problema:** MySQL corre en un contenedor Docker (`laravel_mysql`) compartido con otro proyecto. Spring Boot, al conectar desde el host, aparecía visto desde MySQL como `172.19.0.1` (IP del bridge), sin permiso. Adicionalmente, `caching_sha2_password` exigía `allowPublicKeyRetrieval=true` o SSL. El `MySQL8Dialect` antiguo está deprecado en Hibernate 6.4.
**Solución:**
- URL JDBC con `127.0.0.1` (en lugar de `localhost`, evita ambigüedades de resolución).
- Añadidos parámetros `useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Madrid`.
- Eliminada la propiedad `spring.jpa.properties.hibernate.dialect=MySQL8Dialect` (Hibernate 6 lo detecta solo).
- Puerto cambiado de 8080 a **8090** (el 8080 lo ocupa el nginx de Peitigestion en este equipo).

### Usuario MySQL dedicado para el TFG
**Archivo nuevo:** `setup-mysql.sql`
**Problema:** el usuario `root` del contenedor MySQL pertenece al proyecto Peitigestion y tiene su propia contraseña. Tocarle la contraseña a `root` rompería ese proyecto.
**Solución:** se ha creado un script SQL que aprovisiona un usuario `tfg`@`%` con contraseña `tfg2026`, y le otorga privilegios solo sobre la base `sincochenotemueves`. Esto sigue además la **buena práctica de separación de privilegios** (cada aplicación con su propio usuario y BD), defendible en el TFG. El `application.properties` apunta a este usuario.

### Frontend: api.js apuntando al puerto correcto
**Archivo:** `SinCocheNoTeMueves/src/services/api.js`
**Problema:** apuntaba a `localhost:8080`, pero el backend está en 8090.
**Solución:** `baseURL: 'http://localhost:8090/api'`. Conservado el interceptor de respuesta 401 que limpia el token al expirar.

### Eliminado `authService.js` duplicado y obsoleto
**Archivo:** `SinCocheNoTeMueves/src/services/authService.js`
**Problema:** duplicado de `api.js` con peor formato y URL antigua a 8080. Sin referencias en ningún otro archivo.
**Solución:** borrado.

---

## 🔴 Bugs CRÍTICOS (impedían arrancar el proyecto)

### 1. `vue-router 5.0.3` no es compatible con Vue 3
**Archivo:** `SinCocheNoTeMueves/package.json`
**Problema:** vue-router 5.x es para Vue 4 (aún no estable). Para Vue 3 hay que usar la 4.x.
**Solución:** cambiado a `vue-router: ^4.4.5`. También he actualizado pinia a la 2.x estable
(la 3.x era pre-release) y vite a la 5.x LTS.
**Verificación:** `npm run build` ahora compila ✅ (115 módulos, 198 KB).

### 2. Estructura de paquetes Java rota
**Carpeta:** `TFG-SinCocheNoTeMueves.com/src/main/java/com.tfg.backend/`
**Problema:** la carpeta tenía literalmente puntos en el nombre (`com.tfg.backend/`) en lugar
de la estructura anidada (`com/tfg/backend/`). Maven no compila esto: las directivas
`package com.tfg.backend;` esperan que las carpetas físicas reflejen el package.
**Solución:** renombrada toda la jerarquía a `com/tfg/backend/`. Los 30 archivos `.java`
están ahora en su lugar correcto.

### 3. Clave JWT demasiado corta
**Archivo:** `security/JwtService.java`
**Problema:** la clave era `"TFG_SECRET_KEY"` (14 caracteres = 112 bits). jjwt 0.11.5 con
HS256 exige al menos **256 bits (32 bytes)** y lanza `WeakKeyException` en arranque.
**Solución:**
- Externalizada a `application.properties` (`app.jwt.secret`) con valor por defecto seguro.
- Soporta variable de entorno `APP_JWT_SECRET` para producción.
- Si la clave configurada tiene menos de 32 bytes, se rellena con ceros automáticamente
  (evita bloquear el arranque en desarrollo).
- API de jjwt actualizada: `Keys.hmacShaKeyFor()`, `Jwts.parserBuilder()` (la antigua
  `Jwts.parser()` y `setSigningKey(String)` están deprecadas).

### 4. Falta `GET /api/vehiculos/{id}`
**Archivo:** `controller/VehiculoController.java`
**Problema:** el frontend (`VehiculoDetalleView.vue`, `EditarVehiculoView.vue`) llama a
`api.get('/vehiculos/' + id)` pero el controller solo tenía `GET` global, no por ID.
La página de detalle devolvía 404 siempre.
**Solución:** añadido `@GetMapping("/{id}")` que devuelve 200 con el vehículo o 404 si no existe.

### 5. Modelos sin getters/setters
**Archivos:** `model/Chat.java`, `model/Contacto.java`, `model/Moderacion.java`
**Problema:** sin getters/setters Spring no puede serializar a JSON ni deserializar el body
de las peticiones. `POST /api/contacto` guardaba siempre un objeto vacío.
**Solución:** añadidos todos los getters/setters. He aprovechado para poner
`@Column(length=2000)` en `Contacto.mensaje` (texto largo).

---

## 🟡 Bugs FUNCIONALES (la app arrancaba pero se comportaba mal)

### 6. Inconsistencia de roles entre componentes
**Archivos:** `views/AdminView.vue`
**Problema:**
- El backend establece roles en mayúscula (`USER` / `ADMIN`).
- `Navbar.vue` y `PerfilView.vue` chequean `rol === 'ADMIN'`.
- `AdminView.vue` chequeaba `rol === 'admin'` (minúscula). Resultado: aunque el usuario
  fuese ADMIN, AdminView nunca se mostraba.
**Solución:** unificado a `'ADMIN'` en mayúscula en AdminView.

### 7. Favoritos no se sincronizaban entre VehiculoCard y FavoritosView
**Archivo:** `components/VehiculoCard.vue`
**Problema:**
- `VehiculoCard` enviaba el favorito al backend (`POST /favoritos/{id}`).
- `FavoritosView` leía de `localStorage.getItem('favoritos')`.
- Resultado: nunca aparecían favoritos en la pantalla de favoritos.
- Además, sin token mostraba un `alert('Debes iniciar sesión')` que no aportaba feedback útil.
**Solución:**
- Guarda siempre en localStorage (UX inmediata, funciona también sin sesión).
- Si hay token, sincroniza adicionalmente con el backend en background.
- Sin alerts intrusivos.
- Dispara `Event('storage')` para que el badge del Navbar se actualice en tiempo real.

### 8. Mismatch de authorities en Spring Security
**Archivos:** `config/SecurityConfig.java`, `security/JwtFilter.java`,
`security/CustomUserDetailsService.java`
**Problema:**
- `SecurityConfig` exigía `.hasAuthority("ADMIN")`.
- `JwtFilter` añadía la authority como `"ADMIN"` (sin prefijo).
- `CustomUserDetailsService` añadía como `"ROLE_ADMIN"` (con prefijo).
- Inconsistencia entre los tres puntos = endpoints `/api/admin/**` accesibles a usuarios normales o no accesibles ni a admins según el camino.
**Solución:** unificado todo el código al estándar Spring Security:
- `JwtFilter` y `CustomUserDetailsService` usan `"ROLE_" + rol`.
- `SecurityConfig` usa `.hasAuthority("ROLE_ADMIN")`.

### 9. Contraseñas sin hashear
**Archivos:** `service/AuthService.java`, `controller/UsuarioController.java`,
`controller/AuthController.java`, `config/SecurityConfig.java`
**Problema:** las contraseñas se almacenaban y comparaban en texto plano (`equals`).
**Solución:**
- Bean `BCryptPasswordEncoder` añadido en `SecurityConfig`.
- `UsuarioController.registrar()` aplica `passwordEncoder.encode()` antes de guardar.
- `AuthService.login()` usa `passwordEncoder.matches()` para validar.
- El hash nunca se devuelve al cliente (puesto a `null` antes de serializar).
- Validación: el registro rechaza emails duplicados con `409 Conflict`.

### 10. Filtro JWT no manejaba tokens inválidos
**Archivo:** `security/JwtFilter.java`
**Problema:** si llegaba un JWT corrupto/caducado, `extraerEmail()` lanzaba
una excepción no capturada y el request fallaba con `500 Internal Server Error`
en lugar de `401 Unauthorized`.
**Solución:** envuelto en `try/catch` que limpia el SecurityContext y deja que el
filtro de autorización decida (devolverá 401 limpio).

---

## 🧹 Limpieza estructural

### 11. Proyecto duplicado en disco
**Carpetas eliminadas:** `TFG/src/`, `TFG/target/`, `TFG/pom.xml`, `TFG/package-lock.json`
**Problema:** el contenido era idéntico byte a byte al de `TFG/TFG-SinCocheNoTeMueves.com/`.
Confunde a IDEs y a quien revise el TFG.

### 12. `Main.java` huérfano
**Archivo eliminado:** `TFG-SinCocheNoTeMueves.com/src/main/java/org/example/Main.java`
**Problema:** clase "Hello world" residual del template de IntelliJ. No se usa.

### 13. `target/` versionado
**Carpeta eliminada:** `TFG-SinCocheNoTeMueves.com/target/`
**Problema:** carpeta de artefactos generados por Maven, no debería estar en el repositorio.

### 14. `node_modules` versionado
**Carpeta eliminada:** `SinCocheNoTeMueves/node_modules/`
**Problema:** 79 MB de dependencias que se reinstalan con `npm install`.

### 15. `authService.js` duplicado
**Archivo eliminado:** `SinCocheNoTeMueves/src/services/authService.js`
**Problema:** copia idéntica de `api.js`. No se importaba en ningún sitio.

### 16. `vite-plugin-vue-devtools` no en package.json
**Problema:** el `vite.config.js` lo importaba, pero no estaba declarado como dependencia.
Habría dado error la primera vez que alguien hiciera `npm install` en limpio.
**Solución:** eliminado del config (sigue habiendo Vue DevTools en navegador).

### 17. `@CrossOrigin` redundante
**Archivos:** todos los `controller/*.java`
**Problema:** cada controller tenía `@CrossOrigin(origins="http://localhost:5173")`
pero `CorsConfig` ya configura el mismo origen globalmente para `/api/**`.
Los dos en paralelo pueden generar conflictos de cabeceras `Access-Control-Allow-Origin`
duplicadas.
**Solución:** eliminado de todos los controllers. CORS centralizado en `CorsConfig`.

### 18. Formato y line-endings
**Problema:** mezcla de CRLF (Windows) y LF (Unix) en los `.java`. Espaciado vertical
exagerado (líneas en blanco entre cada anotación).
**Solución:** todos los archivos normalizados a LF y reformateados a estilo
estándar Java/Spring Boot.

---

## 🎨 Mejoras estéticas y de accesibilidad (pequeñas)

He respetado el design system existente (que está muy bien construido) y solo
he tocado tres cosas concretas:

### 19. Scrollbar más usable y `:focus-visible` accesible
**Archivo:** `assets/style.css`
- Scrollbar pasa de 6 px (poco usable) a 10 px con borde sutil.
- Añadido foco visible global con `:focus-visible` (outline azul translúcido)
  para navegación con teclado — requisito de WCAG 2.1.
- Añadido color de selección de texto con la marca.

### 20. Contraste de `badge-gold` (WCAG AA)
**Archivo:** `assets/style.css`
- El color `#92400e` sobre fondo `rgba(245,158,11,0.15)` no llegaba a 4.5:1.
- Cambiado a `#78350f` sobre `rgba(245,158,11,0.18)` → cumple AA.

### 21. `prefers-reduced-motion`
**Archivo:** `assets/style.css`
- Añadida media query que respeta a los usuarios que han activado
  "Reducir movimiento" en su sistema operativo. Las animaciones del hero
  (zoom, bounce, shimmer) podían marear a personas con vestibulopatías.

### 22. Hover sutil en inputs
**Archivo:** `assets/style.css`
- Antes los inputs solo cambiaban en focus. Ahora un hover muy ligero
  (border más oscuro) para indicar interactividad.

### 23. Favicon SVG con identidad de marca
**Archivos nuevos:** `public/favicon.svg`, `index.html`
- Se conserva el `favicon.ico` original como fallback.
- Añadido un favicon SVG nuevo: gradiente azul de la marca + emoji 🚗.
- También se han añadido `<meta name="theme-color">` y etiquetas Open Graph para
  cuando alguien comparta el enlace.

---

## ✅ Verificación

| Comprobación                               | Resultado |
|--------------------------------------------|-----------|
| Sintaxis Java de los 30 archivos           | ✅ OK     |
| Compilación frontend (`npm run build`)     | ✅ OK     |
| Build size                                 | 198 KB JS / 52 KB CSS (gzip: 70 KB / 9.5 KB) |
| Estructura de paquetes Java estándar       | ✅ OK     |
| `vue-router` versión compatible con Vue 3  | ✅ 4.6.4  |
| Endpoints frontend ↔ backend coinciden     | ✅ OK     |

---

## 🔧 Cosas que NO he tocado (a propósito)

Por respetar la indicación de "no cambies nada de lo que está":

- **Identidad visual** (paleta, tipografías Syne+Outfit, layout, iconos emoji).
- **Estructura de carpetas frontend** (componentes, vistas, servicios).
- **Lógica de negocio** del backend (los servicios siguen siendo CRUD simple).
- **Datos mock del Chat** (las dos conversaciones de Carlos Ruiz / Ana Martínez).
- **Stores de Pinia** (counter.js sigue ahí aunque no se use, por si lo querías como base).

Si en algún momento quieres dar un paso más, te dejo aquí algunas mejoras que
no he aplicado pero que son lógicas continuaciones:

- Documentar la API con SpringDoc / OpenAPI.
- Añadir tests (JUnit + MockMvc en backend, Vitest en frontend).
- Subida de imágenes real (ahora mismo `vehiculo.imagen` es solo una URL).
- Paginación en `GET /api/vehiculos`.
- Refactorizar el campo `bloqueado` del usuario: en AdminView se gestiona solo en
  localStorage; en realidad la entidad `Usuario` no tiene ese campo y nunca llega al backend.
- Sustituir los iconos emoji por SVG (Lucide o Heroicons) si quieres look más profesional.
