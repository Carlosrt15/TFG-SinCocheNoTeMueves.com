# 🚗 SinCocheNoTeMueves.com

Plataforma web de compra y venta de vehículos desarrollada como **Trabajo de Fin de Grado (2º DAW)**.

Permite a los usuarios:

- Publicar anuncios de coches
- Buscar y filtrar vehículos
- Contactar con vendedores
- Gestionar perfil, favoritos y mensajes
- (Para administradores) gestionar usuarios y moderar la plataforma

---

## 🛠 Tecnologías utilizadas

### 🎨 Frontend
- HTML5, CSS3, JavaScript
- Vue 3 (Composition API)
- Vue Router 4
- Pinia (gestión de estado)
- Vite (bundler)
- Axios (HTTP)

### ⚙ Backend
- Java 17
- Spring Boot 3.2.5
- Spring Security + JWT (jjwt 0.11.5)
- Spring Data JPA (Hibernate 6.4)
- BCrypt (hash de contraseñas)
- Maven 3.8+

### 🗄 Base de Datos
- MySQL 8

---

## 📂 Estructura del repositorio

```
TFG/
├── README.md
├── CHANGELOG-FIXES.md          ← Detalle de bugs corregidos
├── LICENSE
├── setup-mysql.sql             ← Script SQL para crear BD y usuario
├── SinCocheNoTeMueves/         ← Frontend Vue 3 + Vite
│   ├── src/
│   │   ├── components/         ← Navbar, Footer, VehiculoCard
│   │   ├── views/              ← Home, Login, Vehículos, Admin, …
│   │   ├── services/           ← api.js (axios + interceptores JWT)
│   │   ├── router/             ← rutas + guard de autenticación
│   │   ├── stores/             ← Pinia
│   │   └── assets/style.css    ← design system global
│   ├── package.json
│   ├── vite.config.js
│   └── index.html
└── TFG-SinCocheNoTeMueves.com/ ← Backend Spring Boot
    ├── pom.xml
    └── src/main/
        ├── java/com/tfg/backend/
        │   ├── BackendApplication.java
        │   ├── config/         ← SecurityConfig, CorsConfig
        │   ├── controller/     ← Auth, Usuario, Vehiculo, Admin, Contacto, Favorito
        │   ├── service/        ← lógica de negocio
        │   ├── repository/     ← Spring Data JPA
        │   ├── model/          ← entidades JPA
        │   └── security/       ← JwtService, JwtFilter, CustomUserDetailsService
        └── resources/application.properties
```

---

## ⚙️ Instalación y ejecución

### Requisitos previos
- **Node.js** 18+ (recomendado 20 LTS o 22)
- **JDK** 17+ (Temurin recomendado)
- **Maven** 3.8+
- **MySQL** 8 (local nativo, XAMPP, o Docker)

### 1️⃣ Base de datos

Hay dos opciones según cómo tengas MySQL:

#### Opción A — MySQL en Docker (caso de Mario en este equipo)

En este equipo MySQL corre dentro de un contenedor Docker llamado
`laravel_mysql` (compartido con otro proyecto). Para inicializar la BD del TFG:

```powershell
# Desde la raíz del proyecto TFG
docker exec -i laravel_mysql mysql -u root -p < setup-mysql.sql
```

Te pedirá la contraseña de `root` del contenedor (la del proyecto Peitigestion).
El script crea la base `sincochenotemueves`, el usuario `tfg` con contraseña
`tfg2026` y le da todos los privilegios sobre esa BD.

#### Opción B — MySQL local nativo (Windows / macOS / Linux)

```powershell
mysql -u root -p < setup-mysql.sql
```

> **Nota**: si tu `root` solo acepta conexiones desde `localhost`, puede que
> Spring Boot vea las conexiones llegar desde una IP de bridge virtual
> (`172.x.x.x`). Por eso el usuario `tfg` se crea con `@'%'` (cualquier host).

### 2️⃣ Backend (Spring Boot)

```powershell
cd TFG-SinCocheNoTeMueves.com

# (Opcional, recomendado en producción) exportar el secreto JWT
$env:APP_JWT_SECRET = "cualquier_clave_de_al_menos_32_caracteres_aqui"

mvn spring-boot:run
```

El backend arranca en **http://localhost:8090**.

> **Por qué 8090 y no 8080**: en este equipo el puerto 8080 lo ocupa el
> nginx del proyecto Peitigestion (también en Docker). Si en otro equipo
> tienes el 8080 libre y quieres usarlo, cambia `server.port=8080` en
> `application.properties` y la `baseURL` en `SinCocheNoTeMueves/src/services/api.js`.

### 3️⃣ Frontend (Vue 3 + Vite)

En **otra terminal**:

```powershell
cd SinCocheNoTeMueves
npm install
npm run dev
```

El frontend arranca en **http://localhost:5173**.

### 4️⃣ Probar la app

Abre `http://localhost:5173` en el navegador. Crea una cuenta nueva en
`/registro`, inicia sesión, y ya puedes publicar vehículos, marcar favoritos, etc.

---

## 🔧 Solución de problemas frecuentes

### `Access denied for user 'root'@'172.19.0.1'`
Significa que MySQL ve la conexión desde una IP de bridge virtual (Docker /
WSL2 / Hyper-V) y no tiene un usuario que haga match con esa IP. **Solución**:
ejecuta `setup-mysql.sql` para crear el usuario `tfg`@`%` y asegúrate de que
`application.properties` usa `tfg`/`tfg2026`.

### `Public Key Retrieval is not allowed`
El driver JDBC de MySQL 8 lo exige cuando se usa `caching_sha2_password` sin
SSL. Ya está solucionado: la URL incluye `&allowPublicKeyRetrieval=true`.

### `No compiler is provided in this environment`
Estás ejecutando Maven con un JRE, no con un JDK. Instala un JDK 17:
```powershell
scoop bucket add java
scoop install temurin17-jdk
```

### Errores de "duplicate class" al compilar
Si descomprimiste el ZIP encima de un proyecto antiguo, pueden haber
quedado las dos versiones (con `com.tfg.backend/` y con `com/tfg/backend/`).
Borra todo y descomprime el ZIP en una carpeta limpia.

### El frontend dice `Network Error` o `ERR_CONNECTION_REFUSED`
El backend no está arrancado o no en el puerto 8090. Verifica que la consola
de `mvn spring-boot:run` muestra `Started BackendApplication`.

---

## 🔐 Endpoints REST principales

| Método | Endpoint                       | Auth      | Descripción                       |
|--------|--------------------------------|-----------|-----------------------------------|
| POST   | `/api/auth/login`              | público   | Login → devuelve JWT              |
| POST   | `/api/usuarios/registro`       | público   | Crear cuenta                      |
| GET    | `/api/vehiculos`               | público   | Listar vehículos                  |
| GET    | `/api/vehiculos/{id}`          | público   | Detalle de un vehículo            |
| POST   | `/api/vehiculos`               | usuario   | Publicar vehículo (asigna propietario por JWT) |
| PUT    | `/api/vehiculos/{id}`          | usuario   | Actualizar vehículo               |
| DELETE | `/api/vehiculos/{id}`          | usuario   | Eliminar vehículo                 |
| GET    | `/api/favoritos`               | usuario   | Mis favoritos                     |
| POST   | `/api/favoritos/{vehiculoId}`  | usuario   | Añadir favorito                   |
| DELETE | `/api/favoritos/{id}`          | usuario   | Eliminar favorito                 |
| POST   | `/api/contacto`                | público   | Enviar formulario de contacto     |
| GET    | `/api/admin/usuarios`          | ADMIN     | Listar usuarios                   |
| DELETE | `/api/admin/usuarios/{id}`     | ADMIN     | Eliminar usuario                  |

---

## 🌿 Metodología de trabajo

Sistema de ramas en Git:

- `main` → versión estable
- `feature/*` → nuevas funcionalidades
- `fix/*` → corrección de errores

Cada funcionalidad se desarrolla en su propia rama y se integra mediante Pull Request.

---

## 🚀 Funcionalidades principales

- Registro e inicio de sesión con JWT
- Publicación, edición y eliminación de anuncios
- Listado y filtrado del catálogo (búsqueda, precio, estado, vista grid/lista)
- Página de detalle de vehículo con galería y datos del vendedor
- Sistema de favoritos (sincronizado con backend cuando hay sesión)
- Chat entre comprador y vendedor
- Formulario de contacto persistido en BD
- Panel de administración con gestión de usuarios y log de moderación
- API REST completa con seguridad por roles

---

## 📝 Licencia

Proyecto académico — ver [LICENSE](LICENSE).

Para el detalle completo de los bugs corregidos durante el saneamiento del
proyecto, consulta [CHANGELOG-FIXES.md](CHANGELOG-FIXES.md).
