### Inicio del Proyecto

Este proyecto fue iniciado utilizando **Spring Initializr** ([start.spring.io](https://start.spring.io)), una herramienta oficial de Spring que facilita la creación de proyectos Spring Boot con las dependencias necesarias preconfiguradas.

**Configuración inicial en Spring Initializr:**
- **Project:** Maven
- **Language:** Java
- **Spring Boot:** 3.5.6
- **Java Version:** 21
- **Packaging:** JAR

### Dependencias de Spring Initializr

Las siguientes dependencias fueron seleccionadas durante la creación del proyecto en Spring Initializr:

#### Dependencias Principales (Starters)

1. **Spring Web** (`spring-boot-starter-web`)
   - Para crear aplicaciones web REST
   - Incluye Tomcat embebido, Spring MVC, Jackson

2. **Spring Data JPA** (`spring-boot-starter-data-jpa`)
   - Para acceso a datos con JPA/Hibernate
   - Repositorios, transacciones

3. **Validation** (`spring-boot-starter-validation`)
   - Validación de beans con Bean Validation
   - Anotaciones como `@NotNull`, `@Size`, etc.

4. **Spring Security** (`spring-boot-starter-security`)
   - Autenticación y autorización
   - Protección de endpoints

#### Dependencias de Desarrollo

5. **Spring Boot DevTools** (`spring-boot-devtools`)
   - Hot reload, reinicio automático
   - Útil en desarrollo

6. **Lombok** (`lombok`)
   - Reduce código boilerplate
   - Anotaciones como `@Data`, `@Builder`, `@Getter`, `@Setter`

#### Drivers de Base de Datos

7. **PostgreSQL Driver** (`postgresql`)
   - Driver JDBC para PostgreSQL

8. **H2 Database** (`h2`)
   - Base de datos en memoria para desarrollo/testing
   - Opcional si solo usas PostgreSQL

#### Testing

9. **Spring Boot Test** (`spring-boot-starter-test`)
   - Incluida por defecto
   - JUnit, Mockito, AssertJ, etc.

### Dependencias Agregadas Manualmente

Después de la creación inicial, se agregaron las siguientes dependencias manualmente al `pom.xml`:

- **JJWT** (`jjwt-api`, `jjwt-impl`, `jjwt-jackson` versión 0.11.5)
  - Para generar y validar tokens JWT
  - Implementación de autenticación basada en tokens

---

## Instrucciones de Instalación y Ejecución en el IDE

### Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

- **Java 21** o superior ([Descargar Java](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.6+** (generalmente incluido con el IDE o descargar desde [Maven](https://maven.apache.org/download.cgi))
- **PostgreSQL 15+** ([Descargar PostgreSQL](https://www.postgresql.org/download/))
- **IDE compatible** (IntelliJ IDEA, Eclipse, VS Code, etc.)

### Abrir el Proyecto en el IDE

#### IntelliJ IDEA (Recomendado)

1. **Abrir el proyecto:**
   - Abre IntelliJ IDEA
   - Selecciona `File` → `Open`
   - Navega a la carpeta del proyecto `Primer-Parcial-Spring`
   - Selecciona la carpeta y haz clic en `OK`

2. **Configurar el SDK de Java:**
   - IntelliJ detectará automáticamente el proyecto Maven
   - Si aparece un mensaje sobre el SDK, haz clic en `Setup SDK`
   - Selecciona Java 21 o superior
   - Si no tienes Java 21, ve a `File` → `Project Structure` → `Project` → `SDK` y configura Java 21

3. **Importar dependencias de Maven:**
   - IntelliJ debería importar automáticamente las dependencias
   - Si no, haz clic derecho en `pom.xml` → `Maven` → `Reload Project`
   - Espera a que se descarguen todas las dependencias


4. **Ejecutar la aplicación:**
   - Navega a `src/main/java/com/parcialspring/parcialspring/ParcialspringApplication.java`
   - Haz clic derecho en la clase → `Run 'ParcialspringApplication'`
   - O usa el botón verde de ejecutar junto al método `main()`


#### Visual Studio Code

1. **Instalar extensiones necesarias:**
   - **Extension Pack for Java** (Microsoft)
   - **Spring Boot Extension Pack** (VMware)
   - **Maven for Java** (Microsoft)

2. **Abrir el proyecto:**
   - Abre VS Code
   - `File` → `Open Folder`
   - Selecciona la carpeta `Primer-Parcial-Spring`

3. **Configurar Java:**
   - VS Code detectará automáticamente el proyecto Maven
   - Si aparece un mensaje sobre Java, selecciona Java 21
   - Espera a que se descarguen las dependencias (aparecerá una barra de progreso)

4. **Ejecutar la aplicación:**
   - Abre `ParcialspringApplication.java`
   - Haz clic en el botón `Run` que aparece sobre el método `main()`
   - O usa `F5` para iniciar el debugger
   - O abre la terminal integrada y ejecuta: `./mvnw spring-boot:run` (Linux/Mac) o `mvnw.cmd spring-boot:run` (Windows)

### Configuración Inicial del Proyecto

Antes de ejecutar, asegúrate de:

1. **Configurar la base de datos** (ver sección "Configuración de Base de Datos" más abajo)
2. **Verificar `application.properties`** con las credenciales correctas
3. **Tener PostgreSQL corriendo** en tu máquina local

### Verificar que la Aplicación Está Corriendo

Una vez ejecutada la aplicación, deberías ver en la consola del IDE:

```
Started ParcialspringApplication in X.XXX seconds
```

La aplicación estará disponible en:
- **URL Base:** `http://localhost:8080`
- **API Endpoints:** Según la configuración de tus controladores

---
# Capturas de pantalla del sistema en funcionamiento

## Usuario
 ### Gestion de usuario
 ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/gestionUsuario.png)
 ### Detalle de usuario
 ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/detalleUsuario.png)
 ### Edicion de usuario
 ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/edicionUsuario.png)
 ### eliminar usuario
 ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/eliminarUsuario.png)
 ---
## Cabras
 ### Gestion Cabras
 ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/gestorCaprino.png)
 ### Detalle de Cabras
 ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/detalleCabra.png)
 ### Edicion de Cabras
 ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/edicionCabra.png)
 ### eliminar Cabras
 ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/eliminarCabra.png)
 ---
## Productos
 ### Gestion
 ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/gestionProductos.png)
 ### Detalle de Productos
  ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/detalleProducto.png)
 ### Edicion de Productos
 ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/edicionProducto.png)
 ### eliminar Productos
 ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/eliminarProducto.png)
 ---
## Proveedores
 ### Gestion Proveedores
 ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/gestionProveedores.png)
 ### Detalle de Proveedores
  ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/detalleProveedor.png)
 ### Edicion de Proveedores
  ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/edicionProveedor.png)
 ### eliminar Proveedores
  ![Arquitectura General](https://hector-riano.s3.us-east-1.amazonaws.com/procesosN/eliminarProveedor.png)

## FUNCIONALIDAD DE EXPORTACIÓN DE DATOS

El sistema incluye capacidad de exportar datos a Excel y PDF para las entidades principales: **Proveedores(Supplier)** .

### Endpoints de Exportación

#### Exportar Productos
- **Excel**: `GET http://localhost:8080/suppliers/export/excel`
    - Genera archivo `productos.xlsx` con todos los registros de proveedores
    - Content-Type: `application/octet-stream`

- **PDF**: `GET http://localhost:8080/suppliers/export/pdf`
    - Genera archivo `productos.pdf` con reporte tabular de proveedores
    - Content-Type: `application/pdf`

### Tecnologías Utilizadas

- **Apache POI**: Librería para generación de archivos Excel (.xlsx)
- **iText / OpenPDF**: Librería para generación de documentos PDF

### Respuestas HTTP

| Código | Descripción |
|--------|-------------|
| 200 | Archivo generado exitosamente |
| 500 | Error al generar el archivo |

 