-- =================================================================
--  setup-mysql.sql
--  Script de inicialización de la base de datos para SinCocheNoTeMueves
--
--  Crea:
--    - Base de datos `sincochenotemueves` (utf8mb4 para emojis y acentos)
--    - Usuario `tfg`@`%` con contraseña `tfg2026`
--    - Concede TODOS los privilegios sobre `sincochenotemueves` al usuario `tfg`
--
--  Por qué un usuario propio (`tfg`) en vez de `root`:
--    Buena práctica de seguridad. Cada aplicación con su propio usuario
--    y solo con privilegios sobre su propia base de datos.
--    En esta máquina además es necesario porque MySQL corre en un
--    contenedor Docker compartido con otro proyecto (Peitigestion),
--    cuyo `root` ya está reservado y con otra contraseña.
--
--  Cómo ejecutarlo (en PowerShell, dentro de la carpeta del TFG):
--
--    docker exec -i laravel_mysql mysql -u root -p < setup-mysql.sql
--
--  Te pedirá la contraseña del root del contenedor (la del proyecto
--  Peitigestion). El parámetro `-i` redirige el archivo como entrada.
-- =================================================================

CREATE DATABASE IF NOT EXISTS sincochenotemueves
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Si el usuario ya existe (de un intento anterior), lo recreamos limpio
DROP USER IF EXISTS 'tfg'@'%';

CREATE USER 'tfg'@'%'
    IDENTIFIED WITH caching_sha2_password BY 'tfg2026';

GRANT ALL PRIVILEGES ON sincochenotemueves.* TO 'tfg'@'%';

FLUSH PRIVILEGES;

-- Comprobación: estos SELECT deben devolver fila
SELECT user, host, plugin FROM mysql.user WHERE user = 'tfg';
SHOW DATABASES LIKE 'sincochenotemueves';
