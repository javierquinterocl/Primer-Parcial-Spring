## ARQUITECTURA GENERAL PROYECTO CAPRINO React(Vercel) + Spring Boot(Railway) + Supabase(PostgreSQL)

![Arquitectura General](https://javier-quintero.s3.us-east-2.amazonaws.com/img/Spring+Boot+Arquitectura.png)

###  Frontend — React (Vercel)
Fue desarrollado con **React** y desplegado en **Vercel**.  
Se encarga de la **interfaz de usuario** y de gestionar la comunicación con el backend mediante **solicitudes HTTP** (a través de `axios`).  


### Backend — Spring Boot (Railway)
El servidor está construido sobre **Spring Boot** y desplegado en **Railway**, donde se manejan las reglas de negocio, validaciones y autenticación mediante **JWT (JSON Web Token)**.  


### Base de Datos — Supabase (PostgreSQL)

La base de datos almacena la información de usuarios, registros productivos caprinos y demás entidades del sistema.

### Flujo de Comunicación
1. El usuario interactúa con la aplicación web (React).
2. El frontend realiza peticiones HTTP al backend Railway (Spring Boot).
3. El backend procesa la lógica, accede a la base de datos en Supabase y devuelve las respuestas correspondientes.
4. El frontend actualiza dinámicamente la interfaz con los datos obtenidos.





## Dependencias del Backend (Spring Boot)

| Dependencia | Versión  |
|--------------|------------------|
| spring-boot-starter-data-jpa | 3.5.6 |
| spring-boot-starter-validation | 3.5.6 |
| spring-boot-starter-web | 3.5.6 |
| spring-boot-devtools | runtime / optional |
| h2database | runtime |
| postgresql | runtime |
| lombok | optional |
| spring-boot-starter-test | test |
| spring-boot-starter-security | 3.5.6 |
| jjwt-api | 0.11.5 |
| jjwt-impl | 0.11.5 / runtime |
| jjwt-jackson | 0.11.5 / runtime |

---

##  Dependencias del Frontend (React + Vite)

| Dependencia | Versión |
|--------------|-----------------|
| @radix-ui/react-avatar | ^1.1.10 |
| @radix-ui/react-collapsible | ^1.1.12 |
| @radix-ui/react-dialog | ^1.1.15 |
| @radix-ui/react-dropdown-menu | ^2.1.16 |
| @radix-ui/react-icons | ^1.3.2 |
| @radix-ui/react-label | ^2.1.7 |
| @radix-ui/react-select | ^2.2.6 |
| @radix-ui/react-separator | ^1.1.7 |
| @radix-ui/react-slot | ^1.2.3 |
| @radix-ui/react-switch | ^1.2.6 |
| @radix-ui/react-toast | ^1.2.15 |
| axios | ^1.12.1 |
| class-variance-authority | ^0.7.0 |
| clsx | ^2.1.1 |
| lucide-react | ^0.544.0 |
| react | ^19.1.1 |
| react-dom | ^19.1.1 |
| react-router-dom | ^7.9.1 |
| tailwind-merge | ^2.5.4 |

---

## Dependencias de desarrollo

| Dependencia | Versión  Alcance |
|--------------|------------------|
| @eslint/js | ^9.33.0 |
| @vitejs/plugin-react | ^5.0.0 |
| autoprefixer | ^10.4.21 |
| eslint | ^9.33.0 |
| eslint-plugin-react-hooks | ^5.2.0 |
| eslint-plugin-react-refresh | ^0.4.20 |
| globals | ^16.3.0 |
| postcss | ^8.5.6 |
| tailwindcss | ^3.4.17 |
| vite | ^7.1.2 |


##  FUNCIONAMIENTO DEL SISTEMA

###  FRONTEND (React - Vercel)
El frontend fue desarrollado con **React** utilizando **Vite** como herramienta de construcción.  
Su estructura principal se organiza de la siguiente manera:

- **components/** → Contiene los componentes reutilizables de la interfaz (botones, formularios, tarjetas, etc.).
- **pages/** → Define las páginas principales del sistema y gestiona las rutas mediante `react-router-dom`.
- **services/** → Maneja las peticiones HTTP hacia el backend usando **Axios**, centralizando las llamadas a la API.
- **assets/** → Archivos estáticos como íconos.

El  inicia con la carga de `main.jsx`, donde se renderiza el componente principal `App.jsx`.  
Desde allí se controlan las rutas, la autenticación y la visualización dinámica de la información proveniente del backend.


### Despliegue Frontend en Vercel (React + Vite)

**1. Crear proyecto en Vercel**
- Ir a https://vercel.com → New Project → Import Git Repository (GitHub).
- Seleccionar el repositorio del frontend

**2. Configurar variables de entorno**
- En Vercel → Project Settings → Environment Variables, agregar:
  VITE_API_URL= URL DE BACKEND EN RAILWAY

**3. Deploy automático**
- Habilitar Deploys automáticos con cada push a la rama principal.
- Vercel ejecutará `npm run build` y publicará la carpeta `dist`.

**4. Ajustes extra**

- En `vercel.json` o desde UI de Vercel:
  ```json
  {
    "rewrites": [{ "source": "/(.*)", "destination": "/index.html" }]
  }
  ```

###  BACKEND (Spring Boot - Railway)

El backend fue desarrollado con **Spring Boot** y utiliza una arquitectura por capas que incluye **Model**, **DTO**, **Repository**, **Service** y **Controller**, facilitando la organización y mantenimiento del código.

- **Model** → Define las entidades del sistema que representan las tablas en la base de datos.
- **Repository (DAO)** → Interactúa con la base de datos a través de **Spring Data JPA**, ejecutando operaciones CRUD.
- **Service** → Contiene la lógica de negocio, validaciones y procesamiento de datos antes de llegar al controlador.
- **DTO (Data Transfer Object)** → Facilita la transferencia de información entre las capas sin exponer directamente las entidades.
- **Controller** → Expone endpoints que son consumidos por el frontend.

La base de datos está alojada en **Supabase**, que provee un servicio **PostgreSQL** gestionado.  
La conexión se define en el archivo `application.properties` con las credenciales y el URL generado por Supabase.




### Despliegue Backend en Railway (Spring Boot)

**1. Crear proyecto en Railway**
- Ir a https://railway.app → New Project → Deploy from GitHub.
-  Seleccionar el repositorio del backend.

**2. Configurar build & start**
- Railway detecta `pom.xml` y ejecuta `mvn -B package -DskipTests=true` por defecto.

**3. Configurar variables de entorno (Environment Variables)**
- Desde el dashboard del proyecto → Variables:
- SPRING_DATASOURCE_URL, 
- SPRING_DATASOURCE_USERNAME,
- SPRING_DATASOURCE_PASSWORD,
- JWT_SECRET, 
- SPRING_PROFILES_ACTIVE

**4. CORS y seguridad**
- Habilita CORS para la URL del frontend de Vercel.
- Revisar `SecurityConfig` para permitir `OPTIONS` y endpoints de autenticación.

**5. Despliegue automático**
- Railway despliega automáticamente al hacer push a la rama configurada


---

## FUNCIONALIDAD DE EXPORTACIÓN DE DATOS

El sistema incluye capacidad de **exportar datos a Excel y PDF** para las entidades principales: **Cabras (Goats)** y **Productos (Products)**.

### Endpoints de Exportación

#### Exportar Cabras
- **Excel**: `GET http://localhost:8080/goats/export/excel`
  - Genera archivo `cabras.xlsx` con todos los registros de cabras
  - Content-Type: `application/octet-stream`
  
- **PDF**: `GET http://localhost:8080/goats/export/pdf`
  - Genera archivo `cabras.pdf` con reporte tabular de cabras
  - Content-Type: `application/pdf`

#### Exportar Productos
- **Excel**: `GET http://localhost:8080/products/export/excel`
  - Genera archivo `productos.xlsx` con todos los registros de productos
  - Content-Type: `application/octet-stream`
  
- **PDF**: `GET http://localhost:8080/products/export/pdf`
  - Genera archivo `productos.pdf` con reporte tabular de productos
  - Content-Type: `application/pdf`

### Tecnologías Utilizadas

- **Apache POI**: Librería para generación de archivos Excel (.xlsx)
- **iText / OpenPDF**: Librería para generación de documentos PDF

### Respuestas HTTP

| Código | Descripción |
|--------|-------------|
| 200 | Archivo generado exitosamente |
| 500 | Error al generar el archivo |

### Uso desde Frontend

```javascript
// Ejemplo: Exportar productos a Excel
const exportToExcel = async () => {
  const response = await axios.get('http://localhost:8080/products/export/excel', {
    responseType: 'blob'
  });
  const url = window.URL.createObjectURL(new Blob([response.data]));
  const link = document.createElement('a');
  link.href = url;
  link.setAttribute('download', 'productos.xlsx');
  document.body.appendChild(link);
  link.click();
};
``` 
