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
import { ref, onMounted } from 'vue'
import HeaderAdmin from '@/components/Admin/HeaderAdmin.vue'
import containerServices from '@/services/containerservices'

export default {
  name: 'ContainerView',
  components: {
    HeaderAdmin
  },
  setup() {
    const mostrarModal = ref(false)
    const editando = ref(false)
    const contenedores = ref([])
    const contenedoressinrecolectar = ref([])
    const contenedoresconproblemas = ref([])
    const analisisdensidad = ref([])
    const nuevoContenedor = ref({
      id: null,
      id_waste: '',
      coord_x: '',
      coord_y: '',
      weight: '',
      status: ''
    })

    // Cargar contenedores al montar
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
          console.log("contenedores con problemaas",response.data)
        })
        .catch(error => {
          console.error("Error al obtener contenedores con problemas:", error)
        })
    }



    const obtenerContenedoresSinRecolectar = () => {
      containerServices.ContainersWithoutCollection()
        .then(response => {
          contenedoressinrecolectar.value = response.data
          console.log("contenedores sin recolectar",response.data)
        })
        .catch(error => {
          console.error("Error al obtener contenedores sin recolección:", error)
        })
    }



    const obtenerAnalisisDensidad = () => {
      containerServices.DensityAnalysisContainer()
        .then(response => {
          analisisdensidad.value = response.data
          console.log(response.data)
        })
        .catch(error => {
          console.error("Error al realizar el Análisis de densidad", error)
        })
    }


    onMounted(() => {
      obtenerContenedores()
      obtenerContenedoresSinRecolectar()
      obtenerContenedoresConProblemas()
      obtenerAnalisisDensidad()
    })

    // Abrir modal para nuevo
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

    // Abrir modal para editar
    const abrirModalEditar = (contenedor) => {
      editando.value = true
      mostrarModal.value = true
      nuevoContenedor.value = { ...contenedor }
    }

    // Guardar nuevo
    const guardarContenedor = () => {
      containerServices.createContainer(nuevoContenedor.value)
        .then(() => {
          obtenerContenedores()
          obtenerContenedoresSinRecolectar()
          obtenerContenedoresConProblemas()
          cerrarModal()
        })
        .catch(error => {
          console.error("Error al crear contenedor:", error)
        })
    }

    // Actualizar existente
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

    // Eliminar
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

    // Cerrar modal
    const cerrarModal = () => {
      mostrarModal.value = false
    }

    return {
      mostrarModal,
      editando,
      contenedores,
      contenedoressinrecolectar,
      contenedoresconproblemas,
      analisisdensidad,
      nuevoContenedor,
      abrirModalNuevo,
      abrirModalEditar,
      guardarContenedor,
      actualizarContenedor,
      eliminarContenedor,
      cerrarModal
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
  border-radius: 6px;
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
  border-radius: 5px;
  cursor: pointer;
}

.btn-delete {
  background: #d1655d;
  border: none;
  padding: 6px 10px;
  border-radius: 5px;
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
  border-radius: 6px;
  border: none;
}

.btn-cancel {
  background: #d1655d;
  color: white;
  padding: 8px 16px;
  border-radius: 6px;
  border: none;
}

.scrollable-table {
  max-height: 300px; /* Ajusta la altura máxima según lo que necesites */
  overflow-y: auto;  /* Muestra el scroll cuando la tabla sea más alta que el contenedor */
}

</style>
