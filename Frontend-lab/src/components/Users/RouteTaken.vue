<template>
  <div class="route-taken-view">
    <h1 class="title">Rutas realizadas</h1>

    <div class="container">
      <div class="horizontal-scroll">
        <table class="route-table">
          <thead>
            <tr>
              <th>ID Ruta</th>
              <th>ID Conductor</th>
              <th>Fecha</th>
              <th>Hora Inicio</th>
              <th>Hora Fin</th>
              <th>Estado Ruta</th>
              <th>ID Central</th>
              <th>ID Punto de Retiro</th>
            </tr>
          </thead>

          <tbody>
              <tr v-for="route in routes.data" :key="route.id" >
                <td>{{ route.id }}</td>
                <td>{{ route.id_driver }}</td>
                <td>{{ route.date_ || 'Fecha no disponible' }}</td>
                <td>{{ route.start_time || 'Hora no disponible' }}</td>
                <td>{{ route.end_time || 'Hora no disponible' }}</td>
                <td>{{ route.route_status || 'Estado no disponible' }}</td>
                <td>{{ route.id_central || 'No disponible' }}</td>
                <td>{{ route.id_central_finish || 'No disponible' }}</td>
              </tr>
            </tbody>
        </table>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import routeServices from '@/services/routeservices';
import DriverServices from '@/services/driverservices';
import { jwtDecode } from "jwt-decode";

// Definir variables reactivas
const routes = ref([]); // Lista de rutas asignadas
const userEmail = ref(""); // Email del usuario logueado
const driver = ref(null); // Conductor obtenido desde la API

// Obtener los datos del conductor logueado
async function getDriverData(email) {
  try {

    const response = await DriverServices.getDriverByEmail(email);

    // Si la respuesta es exitosa, asignar los datos al objeto `driver`
    driver.value = response.data;


    // Ahora que tenemos los datos del conductor, obtenemos sus rutas asignadas
    if (driver.value) {
      getallrouteassigned(driver.value); // Llamar a la función para obtener las rutas
    }
  } catch (err) {
    console.error("Error obteniendo los datos del conductor:", err);
  }
}

// Obtener todas las rutas asignadas al conductor logueado
const getallrouteassigned = (driver) => {
  if (!driver || !driver.id) return; // Asegurarnos de que el `driver` tenga un id

  routeServices.getAllRouterByDriverIdFinish(driver.id)
    .then((data) => {
      console.log("Datos recibidos del servicio:", data);
      routes.value = data || [];  // Asegurarse de que 'data' no sea undefined
      console.log("Rutas obtenidas para el conductor:", driver.id, routes.value);

      // Verifica si las rutas tienen los datos correctos antes de asignarlos
      if (routes.value && routes.value.length > 0) {
        routes.value.forEach(route => {
          // Asegúrate de que los campos sean válidos
          console.log("a", route);  // Verifica si la ruta tiene la propiedad 'date_'
          route.date_ = route.date_ || 'Fecha no disponible';
          route.start_time = route.start_time || 'Hora no disponible';
          route.end_time = route.end_time || 'Hora no disponible';
          route.id_central = route.id_central || 'No disponible';
          route.id_central_finish = route.id_central_finish || 'No disponible';
        });
      }
    })
    .catch((error) => {
      console.error("Error al obtener las rutas:", error);
    });
};



onMounted(() => {
  const token = localStorage.getItem('jwt');  // Obtener el token del almacenamiento local

  if (!token) return;  // Si no hay token, no hacer nada

  try {
    const decoded = jwtDecode(token);  // Decodificar el token JWT
    userEmail.value = decoded.sub || decoded.email || null;
    if (userEmail.value) {
      getDriverData(userEmail.value);  // Llamar para obtener los datos del conductor
    }
  } catch (error) {
    console.error("Error al decodificar el token:", error);
  }
});
</script>

<style scoped>
.route-taken-view {
  width: 100%;
  max-width: 1200px;
  margin: 2rem auto;
  background: #fff;
  border-radius: 1.2rem;
  box-shadow: 0 6px 18px rgba(78,83,54,0.10);
  padding: 2rem 1.2rem;
  font-family: system-ui, -apple-system, "Segoe UI", Roboto, Arial;
}

.title {
  font-size: 1.8rem;
  font-weight: 700;
  color: #4e5336;
  margin-bottom: 1.6rem;
  text-align: center;
}

.horizontal-scroll {
  overflow-x: auto;
  padding-bottom: 10px;
}

.route-table {
  width: 100%;
  min-width: 1000px;
  border-collapse: separate;
  border-spacing: 0;
  background: #fff;
  border-radius: 1rem;
  box-shadow: 0 2px 8px rgba(78,83,54,0.08);
}

.route-table th, .route-table td {
  padding: 1rem 1.2rem;
  text-align: left;
  font-size: 1rem;
  border-bottom: 1px solid #eaeaea;
  vertical-align: middle;
}

.route-table th {
  background: linear-gradient(180deg,#5e6541,#52563f);
  color: #fff;
  font-weight: 600;
  letter-spacing: 0.2px;
}

.route-table td {
  color: #4e5336;
  font-weight: 500;
  background: #f7f7f7;
}

.route-table tr:last-child td {
  border-bottom: none;
}

@media (max-width: 900px) {
  .route-taken-view {
    max-width: 98vw;
    padding: 1.2rem 0.5rem;
  }
  .route-table th, .route-table td {
    padding: 0.7rem 0.7rem;
    font-size: 0.95rem;
  }
}

.container {
  overflow-x: auto;
}
</style>
