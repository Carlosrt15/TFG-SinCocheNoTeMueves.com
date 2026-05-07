# 🚗 SinCocheNoTeMueves.com

Plataforma web de compra y venta de vehículos · **TFG 2º DAW**.

---

## ⚡ Arrancar el proyecto (rápido)

Necesitas tener instalados **Java 17+**, **Maven** y **Node.js 18+**. **No hace falta MySQL ni Docker** — el backend usa H2 (BD embebida en el propio Java).

### 1. Backend (en una terminal)

```bash
cd TFG-SinCocheNoTeMueves.com
mvn spring-boot:run
```

Arranca en **http://localhost:8090**.

### 2. Frontend (en otra terminal)

```bash
cd SinCocheNoTeMueves
npm install
npm run dev
```

Arranca en **http://localhost:5173**. Listo: abre esa URL en el navegador.

---

## 🗄️ Sobre la base de datos

El backend utiliza **H2** en modo embebido. Las tablas se crean automáticamente al arrancar y los datos se guardan en `TFG-SinCocheNoTeMueves.com/data/sincoche.mv.db`. **Persisten entre arranques**: tus usuarios, vehículos, etc. siguen ahí cuando paras y vuelves a levantar el backend.

### Consola web de H2 (ver y editar la BD)

Mientras el backend está arrancado, abre en el navegador:

**http://localhost:8090/h2-console**

Datos para conectar:
- **JDBC URL**: `jdbc:h2:file:./data/sincoche`
- **User**: `sa`
- **Password**: *(dejar vacío)*

Click en `Connect` y ya puedes hacer SQL directamente sobre la BD. Útil para defensa, depuración, o para borrar la BD si quieres empezar de cero (solo borra `data/`).

### ¿Cómo cambiar a MySQL?

Si en algún momento quieres usar MySQL en lugar de H2 (por ejemplo en producción), abre `TFG-SinCocheNoTeMueves.com/src/main/resources/application.properties`, comenta el bloque H2 y descomenta el bloque MySQL del final del archivo. Tu código Java NO necesita ningún cambio: JPA/Hibernate hace el trabajo.

---

## 🛠 Tecnologías

| Capa | Tecnologías |
|------|------|
| Frontend | Vue 3, Vue Router 4, Pinia, Vite, Axios |
| Backend | Java 17, Spring Boot 3.2, Spring Security + JWT, Spring Data JPA |
| BD | H2 (desarrollo) / MySQL 8 (compatible) |

---

## 📂 Estructura

```
TFG/
├── README.md
├── LICENSE
├── SinCocheNoTeMueves/         ← Frontend Vue 3 + Vite
└── TFG-SinCocheNoTeMueves.com/ ← Backend Spring Boot
    ├── pom.xml
    ├── data/                   ← BD H2 (se crea al arrancar)
    └── src/main/
        ├── java/com/tfg/backend/
        └── resources/application.properties
```

---

## 🔐 Endpoints REST

| Método | Endpoint | Auth | Descripción |
|--------|----------|------|-------------|
| POST | `/api/auth/login` | público | Login → devuelve JWT |
| POST | `/api/usuarios/registro` | público | Crear cuenta |
| GET | `/api/vehiculos` | público | Listar vehículos |
| GET | `/api/vehiculos/{id}` | público | Detalle de un vehículo |
| POST | `/api/vehiculos` | usuario | Publicar vehículo |
| PUT | `/api/vehiculos/{id}` | usuario | Actualizar vehículo |
| DELETE | `/api/vehiculos/{id}` | usuario | Eliminar vehículo |
| GET | `/api/favoritos` | usuario | Mis favoritos |
| POST | `/api/favoritos/{vehiculoId}` | usuario | Añadir favorito |
| DELETE | `/api/favoritos/{id}` | usuario | Eliminar favorito |
| POST | `/api/contacto` | público | Formulario de contacto |
| GET | `/api/admin/usuarios` | ADMIN | Listar usuarios |
| DELETE | `/api/admin/usuarios/{id}` | ADMIN | Eliminar usuario |

---

## 📝 Licencia

Proyecto académico — ver [LICENSE](LICENSE).
