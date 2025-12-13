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
          <option v-for="c in centrales" :key="c.id" :value="c.id">
            Central {{ c.id }} — {{ c.nombre }}
          </option>
        </select>

        <!-- PUNTO RECOLECCIÓN -->
        <label class="label">Punto de Retiro:</label>
        <select v-model="nuevaRuta.id_pick_up_point" class="input">
          <option disabled value="">Seleccionar punto</option>
          <option v-for="p in puntosRetiro" :key="p.id" :value="p.id">
            Punto {{ p.id }} — {{ p.descripcion }}
          </option>
        </select>

        <!-- CONTENEDORES -->
        <label class="label">Contenedores:</label>
        <div class="checkbox-list">
          <label 
            v-for="c in contenedores" 
            :key="c.id" 
            class="checkbox-item"
          >
            <input 
              type="checkbox" 
              v-model="contenedoresSeleccionados" 
              :value="c.id" 
            />
            <span>
              ID {{ c.id }} — {{ c.status }} — Central {{ c.id_central }}
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
/* MODAL */
const mostrarModal = ref(false)
const contenedores = ref([])
const drivers = ref([])
const centrales = ref([])
const puntosRetiro = ref([])
/* RUTAS PLANIFICADAS */
const rutas = ref([])

/* FORMULARIO */
const nuevaRuta = ref({
  id_driver: "",
  date: "",
  start_time: "",
  end_time: "",
  id_central: "",
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

function guardarRuta() {
  if (!nuevaRuta.value.id_driver || contenedoresSeleccionados.value.length === 0) {
    alert("Debes completar el formulario y seleccionar contenedores.")
    return
  }
  // Build payload for backend
  const payload = {
    contenedores: contenedoresSeleccionados.value,
    idDriver: Number(nuevaRuta.value.id_driver),
    idCentral: Number(nuevaRuta.value.id_central),
    idPickUpPoint: Number(nuevaRuta.value.id_pick_up_point)
  }

  routeServices.createroute(payload)
    .then(res => {
      // on success, refresh routes list or push temporary
      fetchRutas()
      alert('Ruta planificada exitosamente')
    })
    .catch(err => {
      console.error('Error creating route:', err)
      alert('Error al planificar ruta: ' + (err.message || err))
    })

  // RESET local form
  nuevaRuta.value = {
    id_driver: "",
    date: "",
    start_time: "",
    end_time: "",
    id_central: "",
    id_pick_up_point: "",
  }

  contenedoresSeleccionados.value = []
  mostrarModal.value = false
}

async function fetchRutas() {
  try {
    const res = await routeServices.getAllRoutes()
    const data = res
    // routeServices returns parsed JSON via api adapter; ensure array
    const list = Array.isArray(data) ? data : (data.data || [])
    rutas.value = list.map(ruta => ({ ...ruta, contenedores: ruta.contenedores || [] }))
  } catch (e) {
    console.error('Error fetching routes:', e)
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
  } catch (e) {
    console.warn('Could not load pick up points via service:', e)
    puntosRetiro.value = []
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
/* --- igual que antes, no recorto --- */
.container-routes {
  background: #f9f9f9;
  padding: 25px;
  border-radius: 12px;
  width: 1000px;
  margin: 40px auto;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
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

.actions-top {
  display: flex;
  justify-content: flex-end;
}

.btn-plan {
  background: #4a4f37;
  color: #fff;
  padding: 10px 40px;
  border-radius: 8px;
  border: none;
  font-size: 15px;
}

.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0,0,0,0.4);
  backdrop-filter: blur(4px);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal {
  background: white;
  padding: 20px ;
  border-radius: 10px;
  width: 420px;
  color: #333;
  /* make modal vertically scrollable when content is tall */
  max-height: 80vh;
  overflow-y: auto;
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

.checkbox-list {
  background: #f7f7f7;
  border-radius: 8px;
  border: 1px solid #ddd;
  padding: 8px;
  max-height: 240px; /* allow more room but still scroll */
  overflow-y: auto;
  margin-top: 10px;
}

.checkbox-item {
  display: flex;
  gap: 8px;
  margin-bottom: 6px;
}

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

.route-table {
  width: 100%;
  margin-top: 20px;
  border-collapse: collapse;
}

.route-table th {
  background: #52563f;
  color: white;
  padding: 12px;
}

.route-table td {
  background: #f7f7f7;
  padding: 12px;
  border-bottom: 1px solid #ddd;
}

.horizontal-scroll {
  overflow-x: auto;
  max-height: 600px;
}


.Data-text {
  color: #4a4f37;
  text-align: center;
}

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
</style>
