<template>
  <div>
    <HeaderAdmin />
  </div>

  <div class="container-wrapper">
    <h1 class="title">Información de Contenedores</h1>

    <!-- BOTONES SUPERIORES -->
    <div class="actions-top">
      <button class="btn-add" @click="abrirModalNuevo">Agregar contenedor</button>
      <button class="btn-secondary">Recolección reciente</button>
      <button class="btn-mass-update" @click="abrirModalMasivo">Actualización masiva de pesos</button>

    </div>

    <!-- MODAL PARA AGREGAR/EDITAR CONTENEDOR -->
    <div v-if="mostrarModal" class="modal-overlay" @click.self="cerrarModal">
      <div class="modal">
        <h2>{{ editando ? 'Editar Contenedor' : 'Nuevo Contenedor' }}</h2>

        <input v-model="nuevoContenedor.id_waste" type="number" placeholder="ID Waste" />
        <input v-model="nuevoContenedor.coord_x" type="number" placeholder="Coordenada X" />
        <input v-model="nuevoContenedor.coord_y" type="number" placeholder="Coordenada Y" />
        <input v-model="nuevoContenedor.weight" type="number" placeholder="Peso (kg)" />

        <select v-model="nuevoContenedor.status">
          <option value="">Seleccione estado</option>
          <option value="Disponible">Disponible</option>
          <option value="Lleno">Lleno</option>
          <option value="En mantenimiento">En mantenimiento</option>
        </select>

        <div class="modal-buttons">
          <button class="btn-save" @click="editando ? actualizarContenedor() : guardarContenedor()">
            {{ editando ? 'Actualizar' : 'Guardar' }}
          </button>
          <button class="btn-cancel" @click="cerrarModal">Cancelar</button>
        </div>
      </div>
    </div>

    <!--Modal para actualización masiva de pesos de contenedores-->
    <div v-if="mostrarModalMasivo" class="modal-overlay" @click.self="cerrarModalMasivo">
      <div class="modal">
        <h2>Actualizar peso de contenedores</h2>

        <label>Seleccionar ruta:</label>
        <select v-model="selectedRoute" class="input">
          <option disabled value="">Selecciona una ruta</option>
          <option v-for="r in rutas" :key="r.id" :value="r.id">
            Ruta #{{ r.id }}
          </option>
        </select>

        <!-- Mostrar los contenedores asociados -->
        <div v-if="contenedoresDeRuta.length > 0" class="contenedores-info">
          <p class="contenedores-title">Contenedores asociados:</p>
          <div class="contenedores-list">
            <span v-for="cid in contenedoresDeRuta" :key="cid" class="contenedor-chip">
              #{{ cid }}
            </span>
          </div>
        </div>

        <div v-else-if="selectedRoute">
          <p class="contenedores-empty">No hay contenedores asociados a esta ruta.</p>
        </div>


        <label>Nuevo peso (kg):</label>
        <input type="number" v-model="nuevoPeso" class="input" />

        <div class="modal-buttons">
          <button class="btn-save" @click="actualizarPesoMasivo">Actualizar</button>
          <button class="btn-cancel" @click="cerrarModalMasivo">Cancelar</button>
        </div>
      </div>
    </div>



    <!-- TABLA -->
    <div class="grid-header">
      <div>ID</div>
      <div>ID Waste</div>
      <div>Coord X</div>
      <div>Coord Y</div>
      <div>Peso</div>
      <div>Estado</div>
      <div>Acciones</div>
    </div>

    <div class = "scrollable-table">
    <div
      v-for="contenedor in contenedores"
      :key="contenedor.id"
      class="grid-row"
    >
      <div>{{ contenedor.id }}</div>
      <div>{{ contenedor.id_waste }}</div>
      <div>{{ contenedor.coord_x }}</div>
      <div>{{ contenedor.coord_y }}</div>
      <div>{{ contenedor.weight }}</div>
      <div>{{ contenedor.status }}</div>
      <div class="row-actions">
        <button class="btn-edit" @click="abrirModalEditar(contenedor)">Editar</button>
        <button class="btn-delete" @click="eliminarContenedor(contenedor.id)">Eliminar</button>
        </div>
      </div>
    </div>
  </div>

  <div class="container-wrapper2">
    <h1 class  = "title">Contenedores sin recolección</h1>

     <div class="grid-header">
      <div>ID</div>
      <div>Coord X</div>
      <div>Coord Y</div>
      <div>Estado</div>
      <div>Tipo de residuo</div>
    </div>

    <div class = "scrollable-table">
      <div
        v-for="contenedorsinrecolectar in contenedoressinrecolectar"
        :key="contenedorsinrecolectar.id"
        class="grid-row"
      >
        <div>{{ contenedorsinrecolectar.id_contenedor }}</div>
        <div>{{ contenedorsinrecolectar.coord_x }}</div>
        <div>{{ contenedorsinrecolectar.coord_y }}</div>
        <div>{{ contenedorsinrecolectar.ultima_recoleccion }}</div>
        <div>{{ contenedorsinrecolectar.tipo_residuo }}</div>
      </div>
    </div>
  </div>

  <div class = "container-wrapper3">
    <h1 class  = "title">Contenedores  con problemas</h1>

      <div class="grid-header">
        <div>ID</div>
        <div>Coord X</div>
        <div>Coord Y</div>
        <div>Estado</div>
        <div>Tipo de residuo</div>
      </div>

    <div class = "scrollable-table">
      <div
        v-for="contenedorproblema in contenedoresconproblemas"
        :key="contenedorproblema.id"
        class="grid-row"
      >
        <div>{{ contenedorproblema.id_contenedor }}</div>
        <div>{{ contenedorproblema.coord_x }}</div>
        <div>{{ contenedorproblema.coord_y }}</div>
        <div>{{ contenedorproblema.status }}</div>
        <div>{{ contenedorproblema.tipo_residuo }}</div>
      </div>
    </div>
  </div>

  <div class = "container-wrapper4">
    <h1 class = "title">Análisis de Densidad</h1>

    <div class = "grid-header-density">
      <div>Mes</div>
      <div>Promedio Contenedor/Ruta</div>
      <div>Diferencia mes anterior</div>
    </div>

    <div class="scrollable-table">
      <div
        v-for="contenedordensidad in analisisdensidad"
        :key="contenedordensidad.id"
        class="grid-row-density"
      >
        <div>{{ contenedordensidad.month }}</div>
        <div>{{ contenedordensidad.average_containers }}</div>

        <div :style="{ color: contenedordensidad.diff_vs_prev_month > 0 ? '#d1655d' : (contenedordensidad.diff_vs_prev_month < 0 ? 'green' : 'black') }">
          {{ contenedordensidad.diff_vs_prev_month !== null ? contenedordensidad.diff_vs_prev_month : '-' }}
          <span v-if="contenedordensidad.diff_vs_prev_month > 0">⬆</span>
          <span v-if="contenedordensidad.diff_vs_prev_month < 0">⬇</span>
        </div>
      </div>
    </div>
  </div>


</template>

<script>
import { ref, onMounted, watch } from 'vue'
import HeaderAdmin from '@/components/Admin/HeaderAdmin.vue'
import containerServices from '@/services/containerservices'
import routeServices from '@/services/routeservices'
import routeContainerServices from '@/services/route_containerservices'


export default {
  name: 'ContainerView',
  components: {
    HeaderAdmin
  },
  setup() {
    const mostrarModal = ref(false)
    const editando = ref(false)
    const mostrarModalMasivo = ref(false)

    const contenedores = ref([])
    const contenedoressinrecolectar = ref([])
    const contenedoresconproblemas = ref([])
    const contenedoresDeRuta= ref([])
    const analisisdensidad = ref([])
    const rutas = ref([])

    const nuevoContenedor = ref({
      id: null,
      id_waste: '',
      coord_x: '',
      coord_y: '',
      weight: '',
      status: ''
    })

    const selectedRoute = ref('')
    const nuevoPeso = ref('')

    // === CARGAS INICIALES ===
    const obtenerContenedores = () => {
      containerServices.getAllContainers()
        .then(response => {
          contenedores.value = response.data
        })
        .catch(error => {
          console.error("Error al obtener contenedores:", error)
        })
    }

    const obtenerContenedoresConProblemas = () => {
      containerServices.ContainerWithProblems()
        .then(response => {
          contenedoresconproblemas.value = response.data
        })
        .catch(error => {
          console.error("Error al obtener contenedores con problemas:", error)
        })
    }

    const obtenerContenedoresSinRecolectar = () => {
      containerServices.ContainersWithoutCollection()
        .then(response => {
          contenedoressinrecolectar.value = response.data
        })
        .catch(error => {
          console.error("Error al obtener contenedores sin recolección:", error)
        })
    }

    const obtenerAnalisisDensidad = () => {
      containerServices.DensityAnalysisContainer()
        .then(response => {
          analisisdensidad.value = response.data
        })
        .catch(error => {
          console.error("Error al realizar el Análisis de densidad", error)
        })
    }

  const cargarContenedoresDeRuta = async (routeId) => {
    if (!routeId) {
      contenedoresDeRuta.value = []
      return
    }

    try {
      const res = await routeContainerServices.getRouteContainersByRoute(routeId)
      contenedoresDeRuta.value = res.data.map(c => c.id_container)
    } catch (error) {
      console.error("Error al obtener contenedores de la ruta:", error)
      contenedoresDeRuta.value = []
    }
  }


    const obtenerRutas = async () => {
      try {
        const res = await routeContainerServices.getAllRouteContainers()
        const data = res.data || res

        // Eliminar duplicados de rutas (id_route repetidos)
        const unique = []
        const seen = new Set()

        for (const rel of data) {
          if (!seen.has(rel.id_route)) {
            seen.add(rel.id_route)
            unique.push({
              id: rel.id_route
            })
          }
        }

      rutas.value = unique
      } catch (error) {
        console.error('Error al cargar rutas reales:', error)
      }
    }

    watch(selectedRoute, (newRouteId) => {
      cargarContenedoresDeRuta(newRouteId)
    })

    onMounted(() => {
      obtenerContenedores()
      obtenerContenedoresSinRecolectar()
      obtenerContenedoresConProblemas()
      obtenerAnalisisDensidad()
      obtenerRutas()
    })

    // === MODAL NUEVO / EDITAR ===
    const abrirModalNuevo = () => {
      editando.value = false
      mostrarModal.value = true
      nuevoContenedor.value = {
        id: null,
        id_waste: '',
        coord_x: '',
        coord_y: '',
        weight: '',
        status: ''
      }
    }

    const abrirModalEditar = (contenedor) => {
      editando.value = true
      mostrarModal.value = true
      nuevoContenedor.value = { ...contenedor }
    }

    const guardarContenedor = () => {
      containerServices.createContainer(nuevoContenedor.value)
        .then(() => {
          obtenerContenedores()
          cerrarModal()
        })
        .catch(error => {
          console.error("Error al crear contenedor:", error)
        })
    }

    const actualizarContenedor = () => {
      containerServices.updateContainer(nuevoContenedor.value.id, nuevoContenedor.value)
        .then(() => {
          obtenerContenedores()
          cerrarModal()
        })
        .catch(error => {
          console.error("Error al actualizar contenedor:", error)
        })
    }

    const eliminarContenedor = (id) => {
      if (confirm('¿Seguro que deseas eliminar este contenedor?')) {
        containerServices.deleteContainer(id)
          .then(() => {
            obtenerContenedores()
          })
          .catch(error => {
            console.error("Error al eliminar contenedor:", error)
          })
      }
    }

    const cerrarModal = () => {
      mostrarModal.value = false
    }

    const abrirModalMasivo = () => {
      console.log('>>> Modal masivo abierto'); // ← prueba
      mostrarModalMasivo.value = true
    }

    const cerrarModalMasivo = () => {
      mostrarModalMasivo.value = false
      selectedRoute.value = ''
      nuevoPeso.value = ''
    }

    const actualizarPesoMasivo = async () => {
      if (!selectedRoute.value || nuevoPeso.value === '') {
        alert('Debes seleccionar una ruta y un peso válido.')
        return
      }

      const peso = parseFloat(nuevoPeso.value)
      if (peso < 0) {
        alert('El peso no puede ser negativo.')
        return
      }

      try {
        const res = await routeServices.updateContainerWeight(selectedRoute.value, peso)
        alert(res.data || 'Peso actualizado correctamente.')

        // 🔁 Recargar tabla de contenedores
        await obtenerContenedores()

        // 🔁 Cerrar modal y limpiar
        cerrarModalMasivo()
      } catch (error) {
        console.error('Error al actualizar peso masivo:', error)
        alert('Error al actualizar peso: ' + (error.response?.data || error.message))
      }
    }

    return {
      mostrarModal,
      editando,
      contenedores,
      contenedoressinrecolectar,
      contenedoresconproblemas,
      analisisdensidad,
      nuevoContenedor,
      rutas,
      selectedRoute,
      nuevoPeso,
      mostrarModalMasivo,
      abrirModalMasivo,
      cerrarModalMasivo,
      selectedRoute,
      nuevoPeso,
      rutas,
      actualizarPesoMasivo,
      abrirModalNuevo,
      abrirModalEditar,
      guardarContenedor,
      actualizarContenedor,
      eliminarContenedor,
      cerrarModal,
      contenedoresDeRuta,
      cargarContenedoresDeRuta
    }
  }
}
</script>


<style scoped>
.container-wrapper {
  background-color: #f9f9f9;
  padding: 25px;
  border-radius: 12px;
  width: 1000px;
  margin: 40px auto;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.container-wrapper2 {
  background-color: #f9f9f9;
  padding: 25px;
  border-radius: 12px;
  width: 1000px;
  margin: 40px auto;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.container-wrapper3 {
  background-color: #f9f9f9;
  padding: 25px;
  border-radius: 12px;
  width: 1000px;
  margin: 40px auto;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.container-wrapper4 {
  background-color: #f9f9f9;
  padding: 25px;
  border-radius: 12px;
  width: 1000px;
  margin: 40px auto;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.title {
  text-align: center;
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 25px;
  color: #4a4f37;
}

.actions-top {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-bottom: 20px;
}

.btn-add, .btn-secondary {
  padding: 10px 18px;
  background: #4a4f37;
  color: white;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  font-weight: 500;
}

.btn-secondary:hover, .btn-add:hover {
  background: #3c4030;
}

/* Tabla */
.grid-header, .grid-row {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  padding: 12px;
  text-align: center;
  align-items: center;
}

.grid-header {
  background: #52563f;
  color: rgb(255, 255, 255);
  border-radius: 6px;
  font-weight: bold;
}

.grid-row {
  background: white;
  border-bottom: 1px solid #000000;
  color: #000000;
}

/*Tabla 3 columnas*/
.grid-header-density, .grid-row-density {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr; /* 3 columnas de igual tamaño */
  padding: 12px;
  text-align: center;
  align-items: center;
}

.grid-header-density {
  background: #52563f;
  color: rgb(255, 255, 255);
  border-radius: 6px;
  font-weight: bold;
}

.grid-row-density {
  background: white;
  border-bottom: 1px solid #000000;
  color: #000000;
}
/*Fin tabla 3 columnas*/

.row-actions {
  display: flex;
  justify-content: center;
  gap: 10px;
}

.btn-edit {
  background: #ecd674;
  border: none;
  padding: 6px 10px;
  border-radius: 25px;
  cursor: pointer;
}

.btn-delete {
  background: #d1655d;
  border: none;
  padding: 6px 10px;
  border-radius: 25px;
  color: white;
  cursor: pointer;
}

/* Modal */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0,0,0,0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.modal {
  background: rgb(255, 255, 255);
  padding: 25px;
  width: 380px;
  border-radius: 12px;
  box-shadow: 0 0 15px rgba(0,0,0,0.3);
  color: #000000;
  text-align: center;
}

.modal input, .modal select {
  width: 100%;
  padding: 10px;
  margin-bottom: 12px;
  border-radius: 6px;
  border: 1px solid #ccc;
}

.modal-buttons {
  display: flex;
  justify-content: space-between;
}

.btn-save {
  background: #4a4f37;
  color: white;
  padding: 8px 16px;
  border-radius: 25px;
  border: none;
}

.btn-cancel {
  background: #d1655d;
  color: white;
  padding: 8px 16px;
  border-radius: 25px;
  border: none;
}

.scrollable-table {
  max-height: 300px; /* Ajusta la altura máxima según lo que necesites */
  overflow-y: auto;  /* Muestra el scroll cuando la tabla sea más alta que el contenedor */
}

.btn-mass-update {
  background-color: #4a4f37;
  color: white;
  padding: 10px 18px;
  border-radius: 25px;
  border: none;
  cursor: pointer;
  font-weight: 500;
}
.btn-mass-update:hover {
  background-color: #3c4030;
}

.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2000;
}
.modal {
  background: white;
  padding: 20px;
  border-radius: 10px;
  width: 420px;
  color: #333;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.25);
}

.contenedores-info {
  background: #f7f8f3;
  border: 1px solid #cfd3c1;
  border-radius: 10px;
  padding: 12px;
  margin: 10px 0 15px;
  text-align: left;
}

.contenedores-title {
  font-weight: 600;
  color: #4a4f37;
  margin-bottom: 8px;
  font-size: 15px;
}

.contenedores-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.contenedor-chip {
  background-color: #e2e6d5;
  color: #4a4f37;
  padding: 6px 10px;
  border-radius: 20px;
  font-size: 14px;
  font-weight: 500;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
  transition: all 0.2s ease;
}

.contenedor-chip:hover {
  background-color: #d5dac4;
  transform: scale(1.05);
}

.contenedores-empty {
  color: #777;
  font-style: italic;
  margin-top: 8px;
}



</style>
