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

Antes de ejecutar el sistema, es necesario crear y cargar la base de datos en PostgreSQL ejecutando el script **`Creador_de_tabla.sql`**.

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

Para comprender la estructura de la Base de Datos, consultar **`Documentación de la base de datos.pdf`**.

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
* Se insertan rutas con estado "Finalizado" para priorizar visibilidad de la mayoría de funciones. 
* El proyecto está configurado para un entorno de desarrollo local.

---

## Uso del sistema

### Funcionalidades Clave del Sistema

* **Login de Usuario:** Autenticación de conductores y administradores mediante token JWT. El inicio de sesión se encuentra en la esquina superior derecha de la interfaz. Dependiendo del tipo de cuenta, se habilitan los permisos correspondientes. Un administrador puede añadir nuevos administradores desde el menú: `Menú -> Administradores -> Agregar Administrador`.

* **Visualización de Rutas:** Los conductores pueden visualizar en un mapa la ruta asignada del día, incluyendo la ubicación de cada contenedor. Para ello, un administrador debe planificar previamente la ruta desde `Menú -> Rutas -> Planificar Ruta`. Posteriormente, el conductor debe tomar la ruta y visualizarla desde `Menú -> Rutas Asignadas -> Tomar -> Menú -> Inicio`.

* **Gestión de Contenedores:** Permite marcar un contenedor como "vaciado" desde el frontend, lo que actualiza el peso del contenedor en la base de datos. Esta acción puede ser realizada por un administrador editando un contenedor desde `Menú -> Contenedores -> Editar -> Estado = Disponible`.

* **Optimización de Rutas:** La API dispone de un endpoint que, a partir de una lista de contenedores, genera una ruta óptima para su recolección. Este proceso se ejecuta automáticamente cuando un administrador utiliza la opción `Planificar Ruta` (`Menú -> Rutas -> Planificar Ruta`).

* **Monitoreo y Reportes:** Los administradores pueden visualizar resúmenes del desempeño de los conductores, así como el estado de las recolecciones. Estas funcionalidades están disponibles desde `Menú -> Inicio -> Calcular Eficiencia / Obtener Desempeño` y `Menú -> Inicio -> Rutas`.

### Consultas SQL Implementadas

1. **Cálculo de Eficiencia de Recolección:** Devuelve el tiempo promedio que le toma a cada conductor completar una ruta. Muestra el nombre del conductor y el tiempo promedio en horas para todas las rutas completadas en los últimos 6 meses. Visible para el administrador en `Menú -> Inicio -> Calcular Eficiencia`.

2. **Identificación de Contenedores con Problemas:** Encuentra todos los contenedores que han sido recolectados por más de 3 conductores diferentes en el último año. Muestra el ID del contenedor y la cantidad de conductores únicos que lo han vaciado. Visible para el administrador en `Menú -> Contenedores (Contenedores con problemas)`.

3. **Comparación de Desempeño por Tipo de Residuos:** Compara la cantidad total de residuos recolectados por tipo entre los dos conductores con el mayor número de rutas completadas. Muestra el tipo de residuo, la cantidad recolectada por el conductor A y la cantidad recolectada por el conductor B. Visible para el administrador en `Menú -> Inicio -> Obtener Desempeño`.

4. **Detección de Rutas Ineficientes:** Escribe una consulta que identifique las 5 rutas más largas (en número de contenedores) que, sin embargo, fueron completadas en menos tiempo que el 25% de las rutas más cortas. Muestra el ID de la ruta, el número de contenedores y la duración total. Visible para el administrador en `Menú -> Rutas -> Mostrar Rutas Ineficientes`.

5. **Análisis de Densidad de Contenedores:** Calcula el promedio de contenedores por ruta para cada mes en los últimos 12 meses. Muestra el mes y el promedio de contenedores. Además, incluye una columna que muestre la diferencia del promedio del mes actual con respecto al mes anterior. Visible para el administrador en `Menú -> Contenedores (Análisis de Densidad)`.

6. **Simulación de Planificación de Rutas:** Procedimiento almacenado llamado planificar_ruta que recibe un arreglo de IDs de contenedores. El procedimiento valida que todos los contenedores existan y no estén asignados a una ruta pendiente. Si la validación es exitosa, debe crear una nueva ruta con estado 'Pendiente' y asociar a todos los contenedores proporcionados. Visible para el administrador en `Menú -> Rutas -> Planificar Ruta`.

7. **Actualización Masiva de Contenedores:** Dado un ID de ruta, actualiza el peso (basura) estimado de todos los contenedores de esa ruta a un nuevo valor. Si el nuevo valor es negativo, el procedimiento debe lanzar un error y revertir la transacción. Visible para el administrador en `Menú -> Contenedores -> Actualización masiva de pesos`.

8. **Listado de Contenedores sin Recolección Reciente:** Muestra todos los contenedores que no han sido recolectados en los últimos 90 días. La consulta incluye el ID del contenedor, su tipo de residuo, su ubicación y la fecha de su última recolección. Si nunca ha sido recolectado, debe indicarlo. Visible para el administrador en `Menú -> Contenedores (Contenedores sin recolección reciente)`.

9. **Impacto de la Recolección en el Peso Total:** Vista materializada llamada peso_recolectado_diario que muestra la suma total del peso de los residuos recolectados por día. Esta vista debe ser refrescada concurrentemente y debe poder ser consultada rápidamente por la aplicación para generar gráficos de reporte. Visible para el administrador en `Menú -> Inicio (Peso recolectado diario)`.

