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

    onMounted(obtenerContenedores)

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
  max-width: 1000px;
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
  background: white;
  padding: 25px;
  width: 380px;
  border-radius: 12px;
  box-shadow: 0 0 15px rgba(0,0,0,0.3);
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
</style>
