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
              <tr v-for="route in routes.data" :key="route.id">
                <td>{{ route.id }}</td>
                <td>{{ route.id_driver }}</td>
                <td>{{ route.date_ }}</td>
                <td>{{ route.start_time }}</td>
                <td>{{ route.end_time }}</td>
                <td>{{ route.route_status }}</td>
                <td>{{ route.id_central }}</td>
                <td>{{ route.id_pick_up_point }}</td>
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
    console.log("Obteniendo datos del conductor con email:", email);
    const response = await DriverServices.getDriverByEmail(email);

    // Si la respuesta es exitosa, asignar los datos al objeto `driver`
    driver.value = response.data;
    console.log("Datos del conductor:", driver.value);

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
      routes.value = data; // Asignamos las rutas obtenidas al arreglo `routes`
      console.log("Rutas obtenidas para el conductor:", driver.id, data);
    })
    .catch((error) => {
      console.error("Error al obtener las rutas:", error);
    });
};

// Función para tomar la ruta
const takeRoute = (route) => {
  if (route.route_status !== 'Tomada') {
    route.route_status = 'Tomada';  // Cambiar el estado de la ruta a tomada
    console.log(`Ruta tomada con ID: ${route.id}`);
    alert(`Ruta con ID ${route.id} ha sido tomada.`);
  }
};

onMounted(() => {
  const token = localStorage.getItem('jwt');  // Obtener el token del almacenamiento local

  if (!token) return;  // Si no hay token, no hacer nada

  try {
    const decoded = jwtDecode(token);  // Decodificar el token JWT
    console.log("Token decodificado:", decoded);

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
