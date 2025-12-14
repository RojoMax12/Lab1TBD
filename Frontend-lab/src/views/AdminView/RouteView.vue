<template>
  <div>
    <HomeAdminView />
  </div>

  <div class="container-routes">

    <h1 class="title">Planificación de Rutas</h1>

    <!-- BOTÓN PLANIFICAR -->
    <div class="actions-top">
      <button class="btn-plan" @click="mostrarModal = true">
        Planificar Ruta
      </button>
    </div>

    <!-- ============================
          MODAL PLANIFICACIÓN
    ============================ -->
    
    <div v-if="mostrarModal" class="modal-overlay" @click.self="cerrarModal">
      <div class="modal">

        <h2>Planificar Nueva Ruta</h2>

        <!-- SELECCIONAR CONDUCTOR -->
        <label class="label">Conductor:</label>
        <select v-model="nuevaRuta.id_driver" class="input">
          <option disabled value="">Seleccionar conductor</option>
          <option v-for="d in drivers" :key="d.id" :value="d.id">
            {{ d.id }} — {{ d.name }}
          </option>
        </select>

        <!-- FECHA -->
        <label class="label">Fecha:</label>
        <input v-model="nuevaRuta.date" type="date" class="input" />

        <!-- HORAS -->
        <label class="label">Hora inicio:</label>
        <input v-model="nuevaRuta.start_time" type="time" class="input" />

        <label class="label">Hora fin:</label>
        <input v-model="nuevaRuta.end_time" type="time" class="input" />

        <!-- CENTRAL -->
        <label class="label">Central:</label>
        <select v-model="nuevaRuta.id_central" class="input">
          <option disabled value="">Seleccionar central</option>
          <option v-for="c in centrales" :key="c.id" :value="c.id" :disabled="c.id === nuevaRuta.id_central_end">
            Central {{ c.id }} — {{ c.name }}
          </option>
        </select>

        <!-- Central de fin -->
        <label class="label">Central de finalización:</label>
        <select v-model="nuevaRuta.id_central_end" class="input">
          <option disabled value="">Seleccionar central</option>
          <option v-for="ce in centrales" :key="ce.id" :value="ce.id" :disabled="ce.id === nuevaRuta.id_central">
            Central {{ ce.id }} — {{ ce.name }}
          </option>
        </select>

        <!-- CONTENEDORES -->
        <label class="label">Contenedores:</label>
        <div class="checkbox-list">
          <label 
            v-for="co in contenedores" 
            :key="co.id" 
            class="checkbox-item"
          >
            <input 
              type="checkbox" 
              v-model="contenedoresSeleccionados" 
              :value="co.id" 
            />
            <span>
              ID {{ co.id }} — {{ co.status }} — Central {{ co.id_central }}
            </span>
          </label>
        </div>

        <!-- BOTONES -->
        <div class="modal-buttons">
          <button class="btn-save" @click="guardarRuta">Guardar</button>
          <button class="btn-cancel" @click="cerrarModal">Cancelar</button>
        </div>

      </div>
    </div>

    <!-- ============================
          TABLA DE RUTAS PLANIFICADAS
    ============================ -->
    <h2 class="subtitle">Rutas Planificadas</h2>

    <div class="horizontal-scroll">
      <table class="route-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Conductor</th>
            <th>Fecha</th>
            <th>Inicio</th>
            <th>Fin</th>
            <th>Estado</th>
            <th>Central</th>
            <th>Punto Retiro</th>
            <th>Contenedores</th>
            <th>Acciones</th>
          </tr>
        </thead>

        <tbody class="Data-text">
          <tr v-for="r in rutas" :key="r.id">
            <td>{{ r.id }}</td>
            <td>{{ r.id_driver }}</td>
            <td>{{ r.date_ }}</td>
            <td>{{ r.start_time }}</td>
            <td>{{ r.end_time }}</td>
            <td>{{ r.route_status }}</td>
            <td>{{ r.id_central }}</td>
            <td>{{ r.id_pick_up_point }}</td>
            <td>{{ r.contenedores.join(', ') }}</td>
            <td>
              <button class="btn-delete" @click="eliminarRuta(r.id)">Eliminar</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>

  <!-- ============================
        TABLA DE RUTAS INEFICIENTES
  ============================ -->
  <div class="inefficient-card">
    <h2 class="subtitle">Rutas Ineficientes</h2>

    <button class="btn-plan" @click="fetchRutasIneficientes">
      Mostrar Rutas Ineficientes
    </button>

    <div class="inefficient-box">
  <table class="inefficient-table">
    <thead>
      <tr>
        <th>ID Ruta</th>
        <th>Contenedores</th>
        <th>Duración (horas)</th>
      </tr>
    </thead>

    <!-- Si hay datos -->
    <tbody v-if="rutasIneficientes.length" class="Data-text">
      <tr v-for="r in rutasIneficientes" :key="r.route_id">
        <td>{{ r.route_id }}</td>
        <td>{{ r.container_count }}</td>
        <td>{{ r.duration_hours }}</td>
      </tr>
    </tbody>

    <!-- Si NO hay datos -->
    <tbody v-else>
      <tr>
        <td colspan="3" class="no-data">No hay rutas ineficientes cargadas aún.</td>
      </tr>
    </tbody>
  </table>
</div>

  </div>


</template>



<script setup>
import { ref } from 'vue'
import { onMounted } from 'vue'
import HomeAdminView from '@/components/Admin/HeaderAdmin.vue'
// Use direct fetch calls instead of a routeServices wrapper to keep this component self-contained
import driverServices from '@/services/driverservices'
import centralServices from '@/services/centralservices'
import containerServices from '@/services/containerservices'
import routeServices from '@/services/routeservices'
import pickUpServices from '@/services/pickupservices'
import routeContainerServices from '@/services/router_containerservices'
/* MODAL */
const mostrarModal = ref(false)
const contenedores = ref([])
const drivers = ref([])
const centrales = ref([])
const puntosRetiro = ref([])

/* RUTAS PLANIFICADAS */
const rutas = ref([])

/* RUTAS INEFICIENTES */
const rutasIneficientes = ref([])

/* FORMULARIO */
const nuevaRuta = ref({
  id_driver: "",
  date: "",
  start_time: "",
  end_time: "",
  id_central: "",
  id_central_end: "",
  id_pick_up_point: "",
})

const contenedoresSeleccionados = ref([])

async function eliminarRuta(id) {
  if (!id) {
    alert('ID de ruta inválido')
    return
  }
  if (!confirm('¿Confirma que desea eliminar la planificación de ruta #' + id + '?')) {
    return
  }
  try {
    await routeServices.deleteRoute(id)
    alert('Ruta eliminada correctamente')
    fetchRutas()
  } catch (e) {
    console.error('Error deleting route:', e)
    alert('Error al eliminar la ruta: ' + (e.message || e))
  }
}

function cerrarModal() {
  mostrarModal.value = false
}

// Función para guardar una nueva ruta
function guardarRuta() {
  // Primero, asegurémonos de que el formulario esté completo y que se hayan seleccionado contenedores
  if (!nuevaRuta.value.id_driver || contenedoresSeleccionados.value.length === 0) {
    alert("Debes completar el formulario y seleccionar contenedores.")
    return
  }

  // Imprimir los datos capturados en el formulario para verificar
  console.log("Datos de la nueva ruta a guardar:", nuevaRuta.value);
  console.log("Contenedores seleccionados:", contenedoresSeleccionados.value);

  // Construir el payload para enviar al backend
  const payload = {
    contenedores: contenedoresSeleccionados.value,
    idDriver: Number(nuevaRuta.value.id_driver),
    idCentral: Number(nuevaRuta.value.id_central),
    idCentralEnd: nuevaRuta.value.id_central_end ? Number(nuevaRuta.value.id_central_end) : null,
    idPickUpPoint: Number(nuevaRuta.value.id_pick_up_point),
    date: nuevaRuta.value.date,
    startTime: nuevaRuta.value.start_time,
    endTime: nuevaRuta.value.end_time
  };

  console.log("Payload enviado al backend:", payload);

  // Llamar al servicio para crear la ruta
  routeServices.createroute(payload)
    .then(res => {
      // Si la creación fue exitosa, mostramos la respuesta
      console.log("Respuesta de la API:", res);

      // Refrescamos la lista de rutas planificadas
      fetchRutas();

      alert('Ruta planificada exitosamente');
    })
    .catch(err => {
      console.error('Error creando ruta:', err);
      alert('Error al planificar ruta: ' + (err.message || err));
    });

  // Limpiar el formulario después de guardarlo
  nuevaRuta.value = {
    id_driver: "",
    date: "",
    start_time: "",
    end_time: "",
    id_central: "",
    id_central_end: "",
    id_pick_up_point: "",
  }

  contenedoresSeleccionados.value = []
  mostrarModal.value = false
}

async function fetchRutas() {
  try {
    // 1️⃣ Obtener todas las rutas planificadas
    const res = await routeServices.getAllRoutes();
    const rutasData = Array.isArray(res.data) ? res.data : res;

    // 2️⃣ Para cada ruta, obtener sus contenedores asociados
    const rutasConContenedores = await Promise.all(
      rutasData.map(async (ruta) => {
        try {
          const contRes = await routeContainerServices.getRouteContainersByRoute(ruta.id);
          const contenedores = Array.isArray(contRes.data)
            ? contRes.data.map(c => c.id_container) // solo IDs de contenedores
            : [];

          return { ...ruta, contenedores };
        } catch (err) {
          console.error(`No se pudieron cargar contenedores de la ruta ${ruta.id}`, err);
          return { ...ruta, contenedores: [] };
        }
      })
    );

    // 3️⃣ Guardar el resultado en el estado de Vue
    rutas.value = rutasConContenedores;

  } catch (e) {
    console.error('Error fetching routes:', e);
  }
}


async function fetchDrivers() {
  try {
    const res = await driverServices.getAllDrivers()
    drivers.value = Array.isArray(res) ? res : (res.data || [])
  } catch (e) {
    console.warn('Could not load drivers:', e)
    drivers.value = []
  }
}

async function fetchContenedores() {
  try {
    const res = await containerServices.getAllContainers()
    contenedores.value = Array.isArray(res) ? res : (res.data || [])
  } catch (e) {
    console.warn('Could not load contenedores:', e)
    contenedores.value = []
  }
}

async function fetchCentrales() {
  try {
    const res = await centralServices.getAllCentrals()
    centrales.value = Array.isArray(res) ? res : (res.data || [])
  } catch (e) {
    console.warn('Could not load centrales:', e)
    centrales.value = []
  }
}

async function fetchPuntosRetiro() {
  try {
    const res = await pickUpServices.getAllPickUp()
    puntosRetiro.value = Array.isArray(res) ? res : (res.data || [])
    console.log("estos son los puntos de retiro", puntosRetiro.value)
  } catch (e) {
    console.warn('Could not load pick up points via service:', e)
    puntosRetiro.value = []
  }
}

async function fetchRutasIneficientes() {
  try {
    const res = await routeServices.inefficientRoutes()
    rutasIneficientes.value = Array.isArray(res.data) ? res.data : []
  } catch (e) {
    console.error('Error fetching inefficient routes:', e)
    alert('Error al obtener rutas ineficientes')
  }
}

onMounted(() => {
  fetchRutas()
  fetchDrivers()
  fetchContenedores()
  fetchCentrales()
  fetchPuntosRetiro()
})


</script>

<style scoped>
/* === TARJETA PRINCIPAL: RUTAS PLANIFICADAS === */
.container-routes {
  background: #ffffff;
  padding: 25px 30px 40px;
  border-radius: 12px;
  width: 1000px;
  margin: 40px auto;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  position: relative;
}

.title, .subtitle {
  text-align: center;
  color: #4a4f37;
  font-weight: bold;
}

.subtitle {
  margin-top: 30px;
  font-size: 22px;
}

/* === BOTÓN GENERAL === */
.btn-plan {
  background-color: #5f6949;
  color: white;
  padding: 10px 20px;
  border-radius: 25px;
  border: none;
  cursor: pointer;
  font-size: 1rem;
  position: absolute;
  right: 40px;
  top: 25px;
  transition: background-color 0.2s ease;
}

.btn-plan:hover {
  background-color: #4c553a;
}

/* === MODAL === */
/* --- MODAL --- */
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  backdrop-filter: blur(4px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2000; /* sobre todo */
}

.modal {
  position: relative;
  z-index: 2001; /* sobre el overlay */
  background: white;
  padding: 20px;
  border-radius: 10px;
  width: 420px;
  color: #333;
  max-height: 80vh;
  overflow-y: auto;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.25);
}

/* --- TARJETAS GENERALES --- */
.container-routes,
.inefficient-card {
  position: relative;   /* aseguran su posición dentro del flujo */
  overflow: visible;    /* evita que el modal quede oculto */
}


.label {
  margin-top: 10px;
  font-weight: 600;
}

.input {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
  border-radius: 6px;
  border: 1px solid #ccc;
}

/* === LISTA DE CONTENEDORES === */
.checkbox-list {
  background: #f7f7f7;
  border-radius: 8px;
  border: 1px solid #ddd;
  padding: 8px;
  max-height: 240px;
  overflow-y: auto;
  margin-top: 10px;
}

.checkbox-item {
  display: flex;
  gap: 8px;
  margin-bottom: 6px;
}

/* === BOTONES DEL MODAL === */
.modal-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.btn-save {
  background: #4a4f37;
  color: #fff;
  border: none;
  padding: 8px 16px;
  border-radius: 6px;
}

.btn-cancel {
  background: #d1655d;
  color: #fff;
  border: none;
  padding: 8px 16px;
  border-radius: 6px;
}

/* === TABLA GENERAL (Planificadas e Ineficientes) === */
.horizontal-scroll,
.inefficient-box {
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  margin-top: 40px;
  overflow-y: auto;
  max-height: 400px;
  padding: 10px;
}

.route-table,
.inefficient-table {
  width: 100%;
  border-collapse: collapse;
  border-radius: 12px;
  overflow: hidden;
}

/* === ENCABEZADOS === */
.route-table thead th,
.inefficient-table thead th {
  position: sticky;
  top: 0;
  background: #5f6949;
  color: #ffffff;
  font-weight: bold;
  padding: 14px;
  text-align: center;
  border-bottom: 2px solid #4a4f37;
  z-index: 2;
}

.route-table thead tr:first-child th:first-child,
.inefficient-table thead tr:first-child th:first-child {
  border-top-left-radius: 12px;
}
.route-table thead tr:first-child th:last-child,
.inefficient-table thead tr:first-child th:last-child {
  border-top-right-radius: 12px;
}

/* === CELDAS === */
.route-table td,
.inefficient-table td {
  background: #f9f9f9;
  color: #333;
  padding: 12px;
  border-bottom: 1px solid #ddd;
  text-align: center;
}

/* === HOVER === */
.route-table tbody tr:hover,
.inefficient-table tbody tr:hover {
  background: #f0f0f0;
  transition: background 0.2s ease;
}

/* === BOTÓN ELIMINAR === */
.btn-delete {
  background: #d9534f;
  color: #fff;
  border: none;
  padding: 6px 10px;
  border-radius: 6px;
  cursor: pointer;
}

.btn-delete:hover {
  opacity: 0.9;
}

/* === TARJETA RUTAS INEFICIENTES === */
.inefficient-card {
  background: #ffffff;
  padding: 25px 30px 40px;
  border-radius: 12px;
  width: 1000px;
  margin: 40px auto;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  position: relative;
}

.inefficient-card .subtitle {
  text-align: center;
  color: #4a4f37;
  font-size: 22px;
  font-weight: bold;
  margin-bottom: 20px;
}

/* === MENSAJE SIN DATOS === */
.no-data {
  text-align: center;
  color: #4a4f37;
  background: #fff;
  padding: 14px;
  font-style: italic;
  border-bottom-left-radius: 12px;
  border-bottom-right-radius: 12px;
}


</style>
