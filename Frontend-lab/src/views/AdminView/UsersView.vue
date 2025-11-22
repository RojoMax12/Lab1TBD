<template>
  <div>
    <HomeAdminView />
  </div>

  <div class="container-worker">

    <h1 class="title">Gestión de Usuarios</h1>

    <!-- Botón agregar -->
    <div class="actions-top">
      <button class="btn-add" @click="mostrarModal = true">
        Agregar usuario
      </button>
    </div>

    <!-- MODAL EMERGENTE -->
    <div v-if="mostrarModal" class="modal-overlay" @click.self="cerrarModal">
      <div class="modal">
        <h2>Nuevo Usuario</h2>

        <!-- Campos según UserEntity -->
        <input v-model="nuevoUsuario.name" placeholder="Nombre" />
        <input v-model="nuevoUsuario.last_name" placeholder="Apellido" />
        <input v-model="nuevoUsuario.email" placeholder="Email" type="email" />
        <input v-model="nuevoUsuario.password" placeholder="Contraseña" type="password" />

        <div class="modal-buttons">
          <button class="btn-save" @click="guardarUsuario">Guardar</button>
          <button class="btn-cancel" @click="cerrarModal">Cancelar</button>
        </div>
      </div>
    </div>

    <!-- TABLA -->
    <div class="grid-header">
      <div>ID</div>
      <div>Nombre</div>
      <div>Apellido</div>
      <div>Email</div>
      <div>Acciones</div>
    </div>

    <div
      class="grid-row"
      v-for="(u, index) in usuarios"
      :key="index"
    >
      <div>{{ u.id }}</div>
      <div>{{ u.name }}</div>
      <div>{{ u.last_name }}</div>
      <div>{{ u.email }}</div>

      <div class="row-actions">
        <button class="btn-edit">Editar</button>
        <button class="btn-delete">Eliminar</button>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref } from 'vue'
import HomeAdminView from '@/components/Admin/HeaderAdmin.vue'

const mostrarModal = ref(false)

/* =============================
   DATOS DE PRUEBA
============================= */
const usuarios = ref([
  {
    id: 1,
    name: "Carlos",
    last_name: "González",
    email: "carlos@example.com",
    password: "123456"
  },
  {
    id: 2,
    name: "María",
    last_name: "López",
    email: "maria@example.com",
    password: "abcdef"
  },
  {
    id: 3,
    name: "Jorge",
    last_name: "Pérez",
    email: "jorge@example.com",
    password: "password123"
  }
])

/* NUEVO USUARIO */
const nuevoUsuario = ref({
  name: '',
  last_name: '',
  email: '',
  password: ''
})

function cerrarModal() {
  mostrarModal.value = false
}

function guardarUsuario() {
  usuarios.value.push({
    id: usuarios.value.length + 1,
    ...nuevoUsuario.value
  })

  nuevoUsuario.value = {
    name: '',
    last_name: '',
    email: '',
    password: ''
  }

  mostrarModal.value = false
}
</script>

<style scoped>
/* ==========================
   CONTENEDOR PRINCIPAL
========================== */
.container-worker {
  background-color: #f9f9f9;
  padding: 25px;
  border-radius: 12px;
  max-width: 900px;
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

/* ==========================
   BOTÓN AGREGAR
========================== */
.actions-top {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}

.btn-add {
  padding: 10px 18px;
  background: #4a4f37;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.btn-add:hover {
  background: #393d2b;
}

/* ==========================
   TABLA
========================== */
.grid-header {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  background: #52563f;
  color: white;
  padding: 12px;
  border-radius: 6px;
  font-weight: bold;
  text-align: center;
}

.grid-row {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  padding: 12px;
  align-items: center;
  border-bottom: 1px solid #ddd;
  text-align: center;
  background: white;
}

.row-actions {
  display: flex;
  justify-content: center;
  gap: 6px;
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

/* ==========================
   MODAL EMERGENTE
========================== */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.40);
  backdrop-filter: blur(4px);
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
  box-shadow: 0px 8px 25px rgba(0, 0, 0, 0.3);
  animation: fadeInScale 0.25s ease-out;
}

.modal h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #4a4f37;
}

@keyframes fadeInScale {
  from { opacity: 0; transform: scale(0.85); }
  to   { opacity: 1; transform: scale(1); }
}

.modal input {
  width: 100%;
  padding: 10px;
  margin-bottom: 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
}

.modal-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.btn-save {
  background: #4a4f37;
  color: white;
  padding: 8px 16px;
  border: none;
  border-radius: 6px;
}

.btn-cancel {
  background: #d1655d;
  color: white;
  padding: 8px 16px;
  border-radius: 6px;
  border: none;
}
</style>
