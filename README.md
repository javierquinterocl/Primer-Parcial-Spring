# Sistema de Gestión de Proyecto Caprino

##  Descripción del Proyecto

El **Sistema de Gestión de Proyecto Caprino** es una solución tecnológica integral desarrollada para el sector agropecuario, específicamente diseñada para optimizar y digitalizar los procesos de gestión del proyecto caprino de la **Granja Experimental de la Universidad Francisco de Paula Santander Seccional Ocaña**.

Este sistema transforma la recolección manual de datos en un proceso automatizado y eficiente, facilitando el registro, seguimiento y análisis de información crítica relacionada con:
-  Gestión de caprinos (inventario, producción, salud)
- Control de productos y stock
- Administración de proveedores
- Gestión de empleados y usuarios
- Registro de salidas de productos

### Tecnologías Utilizadas

**Backend:**
- Java 17+ con Spring Boot 3.x
- Spring Data JPA para persistencia de datos
- Spring Security con JWT para autenticación
- PostgreSQL como base de datos relacional
- Desplegado en Railway

**Frontend:**
- React 18+
- JavaScript (ES6+) y JSX
- Interfaz intuitiva y responsive
- Diseñado para usuarios sin experiencia técnica

**Base de Datos:**
- PostgreSQL 15+
- Desplegada en Supabase
- Migración desde desarrollo local a producción

---

## Integrantes del Proyecto

| Nombre | Código | Responsabilidades                                       |
|--------|--------|---------------------------------------------------------|
| **Andrés Salas** | 192164 | Gestión de Usuarios y Autenticación y Gestion de cabras |
| **Javier Quintero** | 192163 | Gestión de Productos                                    |
| **Héctor Riaño** | 192112 | Gestión de Proveedores                                  |

---

## Objetivo del Proyecto

Desarrollar un software web de gestión de procesos basado en tecnologías y herramientas modernas que automaticen los procesos de **registro, seguimiento y análisis** de información importante referente a:

- **Caprinos**: Seguimiento del inventario, producción de leche, reproducción y salud
- **Productos**: Control de inventario y gestión de stock
- **Proveedores**: Administración de proveedores y sus datos de contacto
- **Empleados**: Gestión de usuarios y control de acceso
- **Salidas de Productos**: Registro y trazabilidad de movimientos de inventario

Todo esto en el marco del proyecto caprino de la granja experimental de la UFPS Ocaña, mejorando la eficiencia operativa, la trazabilidad de procesos y facilitando la toma de decisiones basadas en datos confiables y actualizados.

---


### Gestión de Usuarios
**Responsable: Andrés Salas - 192164**

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/users` | Crear un nuevo usuario |
| `GET` | `/users` | Listar todos los usuarios |
| `GET` | `/users/{id}` | Obtener usuario por ID |
| `PUT` | `/users/{id}` | Actualizar un usuario existente |
| `DELETE` | `/users/{id}` | Eliminar un usuario |
| `POST` | `/users/login` | Iniciar sesión (autenticación) |
| `POST` | `/users/logout` | Cerrar sesión |

---

### Gestión de Productos
**Responsable: Javier Quintero - 192163**

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/products` | Crear un nuevo producto |
| `GET` | `/products` | Listar todos los productos |
| `GET` | `/products/{id}` | Obtener producto por ID |
| `PUT` | `/products/{id}` | Actualizar un producto existente |
| `DELETE` | `/products/{id}` | Eliminar un producto |


---

### Gestión de Proveedores
**Responsable: Héctor Riaño - 192112**

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/suppliers` | Crear un nuevo proveedor |
| `GET` | `/suppliers` | Listar todos los proveedores |
| `GET` | `/suppliers/{id}` | Obtener proveedor por ID |
| `PUT` | `/suppliers/{id}` | Actualizar un proveedor existente |
| `DELETE` | `/suppliers/{id}` | Eliminar un proveedor |


---

### Gestión de Caprinos
**Responsable: Andrés Salas - 192164**

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/goats` | Registrar una nueva cabra |
| `GET` | `/goats` | Listar todas las cabras |
| `GET` | `/goats/{id}` | Obtener información de una cabra por ID |
| `PUT` | `/goats/{id}` | Actualizar información de una cabra |
| `DELETE` | `/goats/{id}` | Eliminar registro de una cabra |

---

### Gestión de Salidas de Productos
**Responsable: Javier Quintero - 192163**

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/product-outputs` | Registrar una salida de producto |
| `GET` | `/product-outputs` | Listar todas las salidas de productos |
| `GET` | `/product-outputs/{id}` | Obtener salida de producto por ID |
| `DELETE` | `/product-outputs/{id}` | Eliminar registro de salida (devuelve stock) |

**Nota:** Al registrar una salida, el sistema automáticamente reduce el stock del producto. Al eliminar una salida, el stock se devuelve automáticamente.

---

## Estructura de carpetas del Proyecto

```
Primer-Parcial-Spring/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── parcialspring/
│   │   │           └── parcialspring/
│   │   │               ├── config/
│   │   │               │   ├── AppConfig.java
│   │   │               │   ├── CorsConfig.java
│   │   │               │   ├── JwtAuthFilter.java
│   │   │               │   ├── JwtUtil.java
│   │   │               │   └── SecurityConfig.java
│   │   │               │
│   │   │               ├── controller/
│   │   │               │   ├── GoatController.java
│   │   │               │   ├── ProductController.java
│   │   │               │   ├── ProductOutputController.java
│   │   │               │   ├── SupplierController.java
│   │   │               │   └── UserController.java
│   │   │               │
│   │   │               ├── dto/
│   │   │               │   ├── AuthenticationResponse.java
│   │   │               │   ├── GoatRequest.java
│   │   │               │   ├── GoatResponse.java
│   │   │               │   ├── ProductOutputRequest.java
│   │   │               │   ├── ProductOutputResponse.java
│   │   │               │   ├── ProductRequest.java
│   │   │               │   ├── ProductResponse.java
│   │   │               │   ├── SupplierRequest.java
│   │   │               │   ├── SupplierResponse.java
│   │   │               │   ├── UserRequest.java
│   │   │               │   └── UserResponse.java
│   │   │               │
│   │   │               ├── model/
│   │   │               │   ├── GoatModel.java
│   │   │               │   ├── ProductModel.java
│   │   │               │   ├── ProductOutputModel.java
│   │   │               │   ├── SupplierModel.java
│   │   │               │   ├── TokenModel.java
│   │   │               │   └── UserModel.java
│   │   │               │
│   │   │               ├── repository/
│   │   │               │   ├── GoatRepository.java
│   │   │               │   ├── ProductOutputRepository.java
│   │   │               │   ├── ProductRepository.java
│   │   │               │   ├── SupplierRepository.java
│   │   │               │   ├── TokenRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               │
│   │   │               ├── service/
│   │   │               │   ├── GoatService.java
│   │   │               │   ├── ProductOutputService.java
│   │   │               │   ├── ProductService.java
│   │   │               │   ├── SupplierService.java
│   │   │               │   └── UserService.java
│   │   │               │
│   │   │               └── ParcialspringApplication.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │
│   └── test/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

## Configuración de Base de Datos

### Configuración Local (Desarrollo con pgAdmin 4)

Para ejecutar el proyecto localmente con PostgreSQL:

1. **Instalar PostgreSQL y pgAdmin 4**

2. **Crear la base de datos:**
```sql
CREATE DATABASE granme_parcial;
```

3. **Configurar `application.properties`:**
```
# Configuración de PostgreSQL Local
spring.datasource.url=jdbc:postgresql://localhost:5432/granme_parcial
spring.datasource.username=postgres
spring.datasource.password=tu_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect


```
---

### Configuración en Producción (Supabase + Railway)

####  Base de Datos en Supabase

**Paso 1: Crear proyecto en Supabase**
1. Ir a [supabase.com](https://supabase.com) y crear una cuenta
2. Crear un nuevo proyecto
3. Copiar las credenciales de conexión

**Paso 2: Conectar desde local a Supabase (PowerShell)**

```powershell
# Instalar psql (si no está instalado) o en su lugar añadir a PATH de las variables de entorno

# Conectar a tu proyecto
psql "postgresql://postgres.qutysjdohynjyqzzkqbk:[contraseña de supabase]@aws-1-us-east-2.pooler.supabase.com:5432/postgres?sslmode=require"

# Verificar conexión
/dt
```

**Paso 3: Migrar datos desde base de datos local**

```powershell
# 1. Hacer backup de la base de datos local

# 2. Conectar a Supabase y restaurar
/i ruta\al\backup.sql

```

#### Configuracion en Railway de la base de datos

**Configurar Variables de Entorno**

En proyecto spring boot, del archivo aplication.properties, reemplazar las variables de conexión a la base de datos por variables de entorno:

```
# Configuracion de postgre en producción
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
jwt.secret=${JWT_SECRET}
```

En Railway, ir a Variables y agregar:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://db.tu-proyecto.supabase.co:5432/postgres
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=contraseña de supabase
JWT_SECRET=clave de jwt

```
