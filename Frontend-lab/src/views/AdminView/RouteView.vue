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
import routeServices from '@/services/routeservices'

/* MODAL */
const mostrarModal = ref(false)
const contenedores = ref([])
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

function cerrarModal() {
  mostrarModal.value = false
}

function guardarRuta() {
  if (!nuevaRuta.value.id_driver || contenedoresSeleccionados.value.length === 0) {
    alert("Debes completar el formulario y seleccionar contenedores.")
    return
  }

  rutas.value.push({
    id: rutas.value.length + 1,
    ...nuevaRuta.value,
    route_status: "Pendiente",
    contenedores: [...contenedoresSeleccionados.value]
  })

  // RESET
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

function fetchrutas () {
  routeServices.getAllRoutes()
    .then(response => {
      rutas.value = response.data.map(ruta => ({
        ...ruta,
        contenedores: ruta.contenedores || []
      }))
      contenedores.value = response.data.contenedores || []
      console.log(response.data)
    })
    .catch(error => {
      console.error("Error fetching routes:", error)
    })
}

onMounted(() => {
  fetchrutas()
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
  padding: 10px 18px;
  border-radius: 8px;
  border: none;
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
  color: #ffffff;
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
  max-height: 200px;
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
}

.Data-text {
  color: #4a4f37;
  text-align: center;
}
</style>
