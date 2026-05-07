# 🚗 SinCocheNoTeMueves.com

Plataforma web de compra y venta de vehículos desarrollada como **Trabajo de Fin de Grado (2º DAW)**.

El objetivo del proyecto es crear una aplicación moderna, escalable y profesional que permita a los usuarios:

- Publicar anuncios de coches
- Buscar y filtrar vehículos
- Contactar con vendedores
- Gestionar perfiles de usuario
- Administrar la plataforma

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
- Spring Boot 3.2
- Spring Security + JWT (jjwt 0.11.5)
- Spring Data JPA
- BCrypt (hash de contraseñas)
- Maven

### 🗄 Base de Datos
- MySQL 8

---

## 📂 Estructura del repositorio

```
TFG/
├── README.md
├── CHANGELOG-FIXES.md          ← Detalle de bugs corregidos
├── LICENSE
├── SinCocheNoTeMueves/         ← Frontend Vue 3 + Vite
│   ├── src/
│   │   ├── components/         ← Navbar, Footer, VehiculoCard
│   │   ├── views/              ← 13 vistas (Home, Login, Vehículos, …)
│   │   ├── services/           ← api.js, vehiculoService, favoritoService
│   │   ├── router/             ← rutas + guard JWT
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
- **JDK** 17+
- **Maven** 3.8+
- **MySQL** 8+ (corriendo en `localhost:3306`)

### 1️⃣ Base de datos

Crea la BD y un usuario `root` con la contraseña que esté en `application.properties`:

```sql
CREATE DATABASE sincochenotemueves CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

> Si usas otra contraseña, edita `TFG-SinCocheNoTeMueves.com/src/main/resources/application.properties`.

### 2️⃣ Backend (Spring Boot)

```bash
cd TFG-SinCocheNoTeMueves.com

# (Opcional, recomendado en producción) exportar el secreto JWT
export APP_JWT_SECRET="cualquier_clave_de_al_menos_32_caracteres_aqui"

mvn spring-boot:run
```

El backend arranca en `http://localhost:8090`.

### 3️⃣ Frontend (Vue 3 + Vite)

En otra terminal:

```bash
cd SinCocheNoTeMueves
npm install
npm run dev
```

El frontend arranca en `http://localhost:5173`.

---

## 🔐 Endpoints REST principales

| Método | Endpoint                       | Auth      | Descripción                       |
|--------|--------------------------------|-----------|-----------------------------------|
| POST   | `/api/auth/login`              | público   | Login → devuelve JWT              |
| POST   | `/api/usuarios/registro`       | público   | Crear cuenta                      |
| GET    | `/api/vehiculos`               | público   | Listar vehículos                  |
| GET    | `/api/vehiculos/{id}`          | público   | Detalle de un vehículo            |
| POST   | `/api/vehiculos`               | usuario   | Publicar vehículo                 |
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

Sistema de ramas:

<<<<<<< HEAD
- `main` → versión estable
- `feature/*` → nuevas funcionalidades
- `fix/*` → corrección de errores
=======
- `main` → versión estable del proyecto  
- `feature/*` → nuevas funcionalidades  
  
>>>>>>> 7340b0a797af59b36fbda7a40179ce88e6869aa2

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
