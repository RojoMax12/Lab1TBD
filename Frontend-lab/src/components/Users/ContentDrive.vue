<template>
  <div class="container">
    <div class="map-container">
      <img src="/Mapa-ruta.png" alt="Mapa de ruta" />
    </div>

    <div class="info-wrapper">
      <button class="info-button" @click="routeAssigned">
        Rutas asignadas
      </button>

      <div class="info-panel">
        <div class="info-title">
          <h2>Ruta actual</h2>
        </div>
        
        <div class="grid">
          <div>
            <span class="label">Fecha - hora</span>
            <input class="value-input" readonly :value="routeactual.date + ' / ' + routeactual.start_time" />
          </div>
          <div>
            <span class="label">Estado ruta</span>
            <input class="value-input" readonly v-model="routeactual.route_status" />
          </div>
          <div>
            <span class="label">Central inicio</span>
            <input class="value-input" readonly :value="centralStart?.name || 'Cargando...'" />
          </div>
          <div>
            <span class="label">Central fin</span>
            <input class="value-input" readonly :value="centralFinish?.name || 'Cargando...'" />
          </div>
          <div>
            <span class="label">Coord. inicio</span>
            <input 
              class="value-input" 
              readonly 
              :value="`${centralStart?.coord_x || 'N/A'}, ${centralStart?.coord_y || 'N/A'}`" 
            />
          </div>
          <div>
            <span class="label">Coord. fin</span>
            <input 
              class="value-input" 
              readonly 
              :value="`${centralFinish?.coord_x || 'N/A'}, ${centralFinish?.coord_y || 'N/A'}`" 
            />
          </div>
        </div>

        <div class="full-width-box">
          <span class="label">Punto siguiente (Coordenadas)</span>
          <input 
            class="value-input" 
            readonly 
            :value="containersData[0]?.coord_x + ' , ' + containersData[0]?.coord_y|| 'No asignado'" 
          />
        </div>
        
        <div class="full-width-box">
          <span class="label">Tipo de residuo</span>
          <input 
            class="value-input" 
            readonly 
            :value="wastesData[0]?.waste_type || 'N/A'" 
          />
        </div>
        
        <div class="controls-container">
          <button class="nextpoint" @click="handleNextPoint">
            Siguiente punto
          </button>
          <button class="routefinish" @click="complete(routeactual)">
            Ruta completada
          </button>
        </div>
      </div>
    </div>
  </div>
</template>




<script setup>
import { ref, onMounted } from 'vue';
import { jwtDecode } from "jwt-decode";
import { useRouter } from 'vue-router';
// Asume que las rutas de los servicios son correctas
import DriverServices from '@/services/driverservices';
import RouteServices from '@/services/routeservices';
import centralServices from '@/services/centralservices';
import RouteContainer from '@/services/route_containerservices';
import ContainerServices from '@/services/containerservices';
import WasteServices from '@/services/wasteservices';


// Referencias reactivas
const routeactual = ref({
  id_route: null,
  date: 'N/A',
  route_status: 'Pendiente',
  id_central: null,
  id_central_finish: null,
  start_time: 'N/A',
  end_time: 'N/A',
  next_point: 'N/A', 
});

// Inicialización como objetos vacíos para evitar errores de acceso a propiedades
const centralStart = ref({}); 
const centralFinish = ref({}); 
const routecontainers = ref([]); // Lista de la tabla de unión
const containersData = ref([]); // Lista de objetos contenedor
const wastesData = ref([]);   // Lista de objetos waste (residuos)

// Datos del conductor
const name = ref('');
const lastname = ref('');
const userEmail = ref('');
const router = useRouter();


onMounted(() => {
  const token = localStorage.getItem('jwt');
  if (!token) return;

  try {
    const decoded = jwtDecode(token);
    userEmail.value = decoded.sub || decoded.email || null;
    if (userEmail.value) {
      getDriverData(userEmail.value);
    }
  } catch (error) {
    console.error("Error al decodificar token:", error);
  }
});


// Obtener datos del conductor
async function getDriverData(email) {
  try {
    const response = await DriverServices.getDriverByEmail(email);
    const driver = response.data;
    name.value = driver.name;
    lastname.value = driver.last_name;
    if (driver.id) {
      getactualroute(driver.id); 
    }
  } catch (err) {
    console.error("Error obteniendo datos del conductor:", err);
  }
}

// Obtener ruta actual
async function getactualroute(driverId) {
  try {
    const response = await RouteServices.findRouteByStatusAndIdDriver(driverId, 'EnProceso');
    
    if (!response.data) {
      routeactual.value.route_status = 'Sin Ruta';
      return; 
    }
    
    const route = Array.isArray(response.data) ? response.data[0] : response.data;

    // Asignación de propiedades
    routeactual.value = {
      id_route: route.id,
      date: route.date_,
      route_status: route.route_status,
      id_central: route.id_central,
      id_central_finish: route.id_central_finish,
      start_time: route.start_time,
      end_time: route.end_time,
      next_point: route.next_point || 'N/A', 
    };

    // Obtener las centrales de inicio y fin y los contenedores concurrentemente
    await Promise.all([
      fetchCentralesId(route.id_central, route.id_central_finish),
      fetchContainerRoute(route.id) 
    ]);

  } catch (error) {
    console.error("Error al obtener la ruta en proceso:", error);
  }
}

// Función para obtener las centrales por su ID
async function fetchCentralesId(idcentralStart, idcentralFinish) {
  try {
    const [resStart, resFinish] = await Promise.all([
      centralServices.getCentralById(idcentralStart),
      centralServices.getCentralById(idcentralFinish)
    ]);
    
    centralStart.value = resStart.data;
    centralFinish.value = resFinish.data;
  } catch (e) {
    console.warn('Error al cargar las centrales:', e);
    centralStart.value = { name: 'Error' };
    centralFinish.value = { name: 'Error' };
  }
}

// Función para obtener los contenedores y wastes de la ruta
async function fetchContainerRoute(routeId) {
  if (!routeId) return;

  try {
    const resRouteContainers = await RouteContainer.getRouteContainersByRoute(routeId);
    routecontainers.value = resRouteContainers.data;

    // Mapear y crear promesas para obtener cada contenedor y su waste asociado
    const containerPromises = routecontainers.value.map(async (rc) => {
      const resContainer = await ContainerServices.getContainerById(rc.id_container);
      const container = resContainer.data;
      
      let waste = null;
      if (container?.id_waste) {
        const resWaste = await WasteServices.getWasteById(container.id_waste);
        waste = resWaste.data;
      }
      return { container, waste };
    });

    const results = await Promise.all(containerPromises);
    
    containersData.value = results.map(r => r.container).filter(c => c);
    wastesData.value = results.map(r => r.waste).filter(w => w);

  } catch (error) {
    console.error("Error al obtener contenedores y wastes de la ruta:", error);
  }
}


// Función para completar la ruta
function complete(routeactual) {
  if (routeactual && routeactual.id_route) {
    RouteServices.updateRouteStatus(routeactual.id_route, "Finalizada")
      .then(() => {
        alert("Ruta marcada como 'Finalizada' exitosamente.");
        routeactual.route_status = "Finalizada"; 
      })
      .catch(error => {
        console.error("Error al actualizar la ruta:", error);
        alert("Error al finalizar la ruta.");
      });
  } else {
    console.error("La ruta no tiene un ID válido.");
  }
}

// Función placeholder para el siguiente punto
function handleNextPoint() {
    alert("Función 'Siguiente punto' no implementada. Se debe actualizar el estado del contenedor actual y avanzar al siguiente punto en la base de datos.");
    // Aquí iría la lógica para: 
    // 1. Llamar a la API para actualizar el estado del contenedor actual (Ej: "Vaciado").
    // 2. Llamar a la 
    // API para actualizar `routeactual.next_point` al ID/coordenada del siguiente contenedor.
}

function routeAssigned() {
  router.push({ name: 'route-assigned' })
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

.routefinish {
  color:white;
  background: linear-gradient(180deg,#5e6541,#52563f);
  padding: 1rem;
  border-radius: 1rem;
  margin-left: 5rem;
  cursor: pointer;
  box-shadow: 0 4px 14px rgba(94,101,65,0.10);
  transition: background 0.18s, box-shadow 0.18s;
  outline: none;
  border: none;
}

.nextpoint {
  color:white;
  background: linear-gradient(180deg,#5e6541,#52563f);
  padding: 1rem;
  border-radius: 1rem;
  margin-left: 5rem;
  cursor: pointer;
  box-shadow: 0 4px 14px rgba(94,101,65,0.10);
  transition: background 0.18s, box-shadow 0.18s;
  outline: none;  
  border: none;

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