# Laboratorio N°1 - Taller de Bases de Datos

Proyecto correspondiente al **Laboratorio 1 de Taller de Bases de Datos (TBD)**. Este repositorio contiene un sistema compuesto por un **Backend en Spring Boot (Java)**, un **Frontend en Vue.js 3** y una **Base de Datos PostgreSQL**, comunicados mediante peticiones HTTP y con autenticación basada en JWT.

El **Objetivo** es desarrollar una plataforma integral para una empresa de recolección de basura, que permita la gestión de conductores, rutas y contenedores, optimizando la asignación de rutas y proveyendo una interfaz para que los conductores visualicen y gestionen sus tareas.

---

## Integrantes

* Antonia Cisternas
* Fabian Escobedo
* José Muñoz
* Johan Neira
* Nicole Torres

---

## Tecnologías

* **Base de Datos:** PostgreSQL
* **Backend:** Spring Boot con Java
* **Frontend:** Vue.js 3
* **Comunicación:** Axios para llamadas HTTP
* **Seguridad:** JSON Web Tokens (JWT) para autenticación y autorización
* **Control de Versiones:** Git y repositorio en GitHub

---
## Clonación del Repositorio

Antes de ejecutar el proyecto, es necesario clonar el repositorio desde GitHub.

Desde una terminal, ejecutar:

```bash
git clone https://github.com/RojoMax12/Lab1TBD.git
```

## Ejecución del Proyecto

### 1. Creación de la Base de Datos

Antes de ejecutar el sistema, es necesario crear la base de datos en PostgreSQL ejecutando el script **`Creador_de_tabla.sql`**.

Se puede realizar desde **Query Tool** o desde la terminal:

**Windows**

```bash
createdb -U postgres TBDlab
psql -U postgres -d TBDlab -f Creador_de_tabla.sql
```

**Linux**

```bash
createdb -U postgres TBDlab && psql -U postgres -d TBDlab -f Creador_de_tabla.sql
```

---

### 2. Configuración del Backend

Para la correcta inicialización del sistema, se debe verificar la conexión del backend con la base de datos. Esta configuración se encuentra definida en el archivo:

```text
backend/src/main/resources/application.properties
```

Configuración por defecto:

```properties
spring.application.name=backend
server.port=8080

db.url=jdbc:postgresql://localhost:5432/TBDlab
db.username=postgres
db.password=postgres
```

Estos parámetros pueden ajustarse según la configuración local del entorno.

---

### 3. Ejecución del Backend

El backend se inicializa ejecutando la clase principal:

```text
BackendApplication
```

O alternativamente desde la terminal:

```bash
mvn clean install
mvn spring-boot:run
```

El backend quedará disponible en:

```text
http://localhost:8080
```

---

### 4. Ejecución del Frontend

Para ejecutar el frontend, es necesario instalar previamente las dependencias del proyecto.

Desde la carpeta del frontend:

```bash
npm install
npm install lucide-vue-next
```

Para iniciar el servidor de desarrollo:

```bash
npm run dev
```

---

### Consideraciones

* PostgreSQL debe estar en ejecución antes de iniciar el backend.
* El script SQL debe ejecutarse antes del primer inicio del sistema.
* El proyecto está configurado para un entorno de desarrollo local.

---

## Uso del sistema


