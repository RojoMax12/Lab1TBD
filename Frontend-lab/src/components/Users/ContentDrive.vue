<template>
  <div class="container">
    <!-- Mapa -->
    <div class="map-container">
      <img src="/Mapa-ruta.png" alt="Mapa de ruta" />
    </div>

    <!-- Panel de información -->
    <div class="info-wrapper">
      <button class="info-button" @click="routeAssigned">
        <svg width="22" height="22" viewBox="0 0 22 22" fill="none" aria-hidden="true" style="vertical-align:middle;margin-right:8px;">
          <circle cx="11" cy="11" r="10" stroke="#ffffff" stroke-width="2"/>
          <path d="M7 11h8m0 0l-3-3m3 3l-3 3" stroke="#ffffff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
        Rutas asignadas
      </button>

      <div class="info-panel">
        <div class="info-title">
            <h2>Ruta actual </h2>
        </div>
        <div class="grid">
          <div>
            <span class="label">Fecha - hora</span>
            <input class="value-input" readonly v-model="routeactual.date" />
          </div>
          <div>
            <span class="label">Estado ruta</span>
            <input class="value-input" readonly v-model="routeactual.route_status" />
          </div>
          <div>
            <span class="label">Central inicio</span>
            <input class="value-input" readonly v-model="routeactual.id_central" />
          </div>
          <div>
            <span class="label">Central fin</span>
            <input class="value-input" readonly v-model="routeactual.id_central_finish" />
          </div>
          <div>
            <span class="label">Coordenada inicio</span>
            <input class="value-input" readonly v-model="routeactual.start_coords" />
          </div>
          <div>
            <span class="label">Coordenada fin</span>
            <input class="value-input" readonly v-model="routeactual.end_coords" />
          </div>
        </div>

        <div class="full-width-box">
          <span class="label">Punto siguiente</span>
          <input class="value-input" readonly v-model="routeactual.next_point" />
        </div>
        <div class="full-width-box">
          <span class="label">Tipo de residuo</span>
          <input class="value-input" readonly v-model="routeactual.waste_type" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import DriverServices from '@/services/driverservices';
import RouteServices from '@/services/routeservices';
import { jwtDecode } from "jwt-decode";

const router = useRouter();

const name = ref('');
const lastname = ref('');
const userEmail = ref('');
const routeactual = ref({
  date: '',
  route_status: '',
  id_central: '',
  id_central_finish: '',
  start_coords: '',
  end_coords: '',
  next_point: '',
  waste_type: ''
});

onMounted(() => {
  const token = localStorage.getItem('jwt');
  if (!token) return;

  try {
    const decoded = jwtDecode(token);
    console.log("TOKEN DECODIFICADO:", decoded);
    userEmail.value = decoded.sub || decoded.email || null;

    if (userEmail.value) {
      getDriverData(userEmail.value);
    }
  } catch (error) {
    console.error("Error al decodificar token:", error);
  }
});

async function getDriverData(email) {
  try {
    console.log("Obteniendo driver con email:", email);
    const response = await DriverServices.getDriverByEmail(email);

    // Asignar datos al conductor
    const driver = response.data;
    console.log("Datos del driver:", driver);
    name.value = driver.name;
    lastname.value = driver.last_name;

    if (driver.id) {
      // Obtener ruta asignada en "En Proceso"
      getactualroute(driver.id);
    }
  } catch (err) {
    console.error("Error obteniendo admin:", err);
  }
}

function getactualroute(driverId) {
  try {
    const response = RouteServices.findRouteByStatusAndIdDriver(driverId, 'EnProceso');
    console.log("Response data:", response.data);
    
    // Check if the route exists
    if (response.data && response.data.length > 0) {
      const route = response.data[0]; // Assuming only one route is returned

      // Update the route details in the UI
      routeactual.value.date = route.date_;
      routeactual.value.route_status = route.route_status;
      routeactual.value.id_central = route.id_central;
      routeactual.value.id_central_finish = route.id_central_finish;
      routeactual.value.start_coords = `${route.coord_x_start}, ${route.coord_y_start}`;
      routeactual.value.end_coords = `${route.coord_x_end}, ${route.coord_y_end}`;
      routeactual.value.next_point = route.next_point || ''; // Assuming next_point might not be set
      routeactual.value.waste_type = route.waste_type || ''; // Assuming waste_type might not be set
    } else {
      console.error("No se encontró una ruta en proceso para este conductor.");
    }
  } catch (error) {
    console.error("Error al obtener la ruta en proceso:", error);
  }
}


function routeAssigned() {
  router.push({ name: 'route-assigned' }); // Redirige a la vista de ruta asignada
}
</script>

<style scoped>

.info-title h2 {
  color: #4a4f37; /* color visible */
  margin: 0 0 0.6rem 0;
  font-size: 1.25rem;
  line-height: 1.2;
  font-weight: 700;
  text-align: center;
}
.container {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  width: 100%;
  max-width: 1200px;
  margin: 2rem auto;
}

.value-input {
  width: 100%;
  background: rgba(255,255,255,0.85);
  border: 1.5px solid #dcd6c8;
  color: #4e5336;
  font-size: 1.08rem;
  font-weight: 700;
  padding: 0.35rem 0.7rem;
  border-radius: 0.5rem;
  outline: none;
  pointer-events: none;
  box-shadow: 0 1px 4px rgba(78,83,54,0.08);
  text-align: left;
  margin-top: 0.15rem;
  margin-bottom: 0.15rem;
}

.value-input:read-only {
  background: rgba(255,255,255,0.85);
  border: 1.5px solid #dcd6c8;
}

@media (min-width: 768px) {
  .container {
    flex-direction: row;
    align-items: flex-start;
  }
}

.map-container {
  flex: 1;
  background: #fff;
  border-radius: 1.2rem;
  box-shadow: 0 6px 18px rgba(78,83,54,0.10);
  padding: 1.2rem;
  display: flex;
  justify-content: center;
  align-items: center;
  min-width: 320px;
  transform: translateY(120px);

}

.map-container img {
  max-width: 100%;
  height: auto;
  border-radius: 1rem;
  box-shadow: 0 2px 8px rgba(78,83,54,0.08);
  border: 1px solid #eaeaea;
}

.info-wrapper {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  gap: 1.2rem;
  min-width: 320px;
}

.info-button {
  background: linear-gradient(180deg,#5e6541,#52563f);
  color: #fff;
  border: none;
  padding: 1rem 2.5rem;
  border-radius: 999px;
  cursor: pointer;
  font-weight: 600;
  font-size: 1.1rem;
  align-self: center;
  box-shadow: 0 4px 14px rgba(94,101,65,0.10);
  display: flex;
  align-items: center;
  gap: 0.5rem;
  transition: background 0.18s, box-shadow 0.18s;
  outline: none;
}

.info-button:hover,
.info-button:focus {
  background: #4a4f37;
  box-shadow: 0 6px 18px rgb(255, 255, 255);
}

.info-panel {
  background: #f7f7f7;
  border-radius: 1.2rem;
  box-shadow: 0 6px 18px rgba(78,83,54,0.10);
  padding: 2rem 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
}

.grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1.2rem;
}

.grid div {
  background: linear-gradient(180deg,#5e6541,#52563f);
  color: #fff;
  text-align: left;
  padding: 1rem 1.2rem;
  border-radius: 0.7rem;
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
  font-size: 1rem;
  box-shadow: 0 2px 8px rgba(78,83,54,0.08);
}

.label {
  font-size: 0.98rem;
  font-weight: 500;
  color: #dcd6c8;
  letter-spacing: 0.2px;
}

.value {
  font-size: 1.08rem;
  font-weight: 700;
  color: #fff;
}

.full-width-box {
  background: linear-gradient(180deg,#5e6541,#52563f);
  color: #fff;
  text-align: left;
  padding: 1rem 1.2rem;
  border-radius: 0.7rem;
  margin-top: 0.5rem;
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
  font-size: 1rem;
  box-shadow: 0 2px 8px rgba(78,83,54,0.08);
}

@media (max-width: 900px) {
  .container {
    flex-direction: column;
    gap: 1.5rem;
    max-width: 98vw;
  }
  .map-container,
  .info-wrapper {
    min-width: 0;
  }
  .info-panel { padding: 1.2rem 0.7rem; }
  .grid div, .full-width-box { font-size: 0.97rem; padding: 0.7rem 0.7rem; }
}
</style>