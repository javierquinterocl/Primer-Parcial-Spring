# Proyecto Granme Spring Boot

## Instrucciones del Proyecto:

###   Javier Quintero - 192163

- Controlador productos de cabra

1. **GET - Listar todos los productos**
    - **Método:** GET
    - **Endpoint:**  
      `http://localhost:8080/products`

2. **GET - Listar producto por id**
    - **Método:** GET
    - **Endpoint:**  
      `http://localhost:8080/products/{id}`

3. **POST - Agregar un nuevo producto**
    - **Método:** POST
    - **Endpoint:**  
      `http://localhost:8080/products`

4. **PUT - Actualizar un producto existente**
    - **Método:** PUT
    - **Endpoint:**  
      `http://localhost:8080/products/{id}`

5. **DELETE - Eliminar un producto**
    - **Método:** DELETE
    - **Endpoint:**  
      `http://localhost:8080/products/{id}`

---

### Andres Salas - 192164

- Controlador perfil de cabra

1. **GET - Listar todos los usuarios**
    - **Método:** GET
    - **Endpoint:**  
      `http://localhost:8080/users`

2. **GET - Listar usuario por id**
    - **Método:** GET
    - **Endpoint:**  
      `http://localhost:8080/users/{id}`

3. **POST - Agregar una nuevo usuario**
    - **Método:** POST
    - **Endpoint:**  
      `http://localhost:8080/users`

4. **PUT - Actualizar un usuario existente**
    - **Método:** PUT
    - **Endpoint:**  
      `http://localhost:8080/users/{id}`

5. **DELETE - Eliminar un usuario**
    - **Método:** DELETE
    - **Endpoint:**  
      `http://localhost:8080/users/{id}`

---

## Hector Riaño - 192112

- Controlador proveedores

1. **GET - Listar todos los proveedores**
    - **Método:** GET
    - **Endpoint:**  
      `http://localhost:8080/suppliers`

2. **GET - Listar proveedor por id**
    - **Método:** GET
    - **Endpoint:**  
      `http://localhost:8080/suppliers/{id}`

3. **POST - Agregar un nuevo proveedor**
    - **Método:** POST
    - **Endpoint:**  
      `http://localhost:8080/suppliers`

4. **PUT - Actualizar un proveedor existente**
    - **Método:** PUT
    - **Endpoint:**  
      `http://localhost:8080/suppliers/{id}`

5. **DELETE - Eliminar un proveedor**
    - **Método:** DELETE
    - **Endpoint:**  
      `http://localhost:8080/suppliers/{id}`

---

## Estructura del Proyecto

```
Primer-Parcial-Spring/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── parcialspring/
│       │           └── parcialspring/
│       │               ├── controller/
│       │               │   ├── ProductController.java
│       │               │   ├── SupplierController.java
│       │               │   └── UserController.java
│       │               ├── dto/
│       │               │   ├── ProductRequest.java
│       │               │   ├── ProductResponse.java
│       │               │   ├── SupplierRequest.java
│       │               │   ├── SupplierResponse.java
│       │               │   ├── UserRequest.java
│       │               │   └── UserResponse.java
│       │               ├── model/
│       │               │   ├── ProductModel.java
│       │               │   ├── SupplierModel.java
│       │               │   └── UserModel.java
│       │               ├── repository/
│       │               │   ├── ProductRepository.java
│       │               │   ├── SupplierRepository.java
│       │               │   └── UserRepository.java
│       │               ├── service/
│       │               │   ├── ProductService.java
│       │               │   ├── SupplierService.java
│       │               │   └── UserService.java
│       │               └── ParcialSpringApplication.java
│       └── resources/
│           ├── application.properties
│           └── static/
├── pom.xml
└── README.md
```

---

## Dependencias Utilizadas

- **Spring Boot Starter Web**: Para crear aplicaciones web y servicios RESTful.
- **Spring Boot Starter Data JPA**: Para la integracion con JPA y acceso a bases de datos relacionales.
- **Spring Boot Starter Validation**: Para validaciones automaticas en los modelos y dtos.
- **Spring Boot DevTools**: Herramientas para desarrollo y recarga automática.
- **H2 Database**: Base de datos en memoria para pruebas y desarrollo.
- **PostgreSQL**: Base de datos relacional utilizada en produccion.
- **Lombok**: Simplifica la escritura de código eliminando la necesidad de escribir getters, setters y constructores.
- **Spring Boot Starter Test**: Herramientas para pruebas 

---

## Tabla de Versionado

| Versión | Fecha      | Descripción                                       | Autor(es)                    |
|---------|------------|---------------------------------------------------|------------------------------|
| 1.0     | 2025-10-02 | Desarrollo de endpoints: user, product y supplier | Javier, Andres, Hector       |
