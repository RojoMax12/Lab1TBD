<template>
  <header class="header">
    <div class="logo-container">
      <img src="/logo.png" alt="Logo" class="logo" />
      <h1 class="site-title">Trash TBD</h1>   
    </div>

    <!-- Botón para abrir modal -->
    <button @click="showLogin = true" class="login-btn">
      Iniciar sesión
    </button>

    <!-- Modal -->
    <LoginModal v-if="showLogin" @close="showLogin = false" />
  </header>
</template>

<script setup>
import { ref } from "vue"
import LoginModal from "./LoginModal.vue" // ajusta la ruta si está en otra carpeta

const showLogin = ref(false)
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #5e6541;
  padding: 1rem;
  color: white;
}

.logo-container {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.logo {
  width: 40px;
  height: 40px;
}

.site-title {
  font-size: 1.5rem;
  font-weight: bold;
}

.login-btn {
  background-color: white;
  color: #5e6541;
  padding: 0.5rem 1rem;
  border-radius: 2rem;
  border: none;
  cursor: pointer;
  font-weight: bold;
}

.login-btn:hover {
  background-color: #ddd;
}
</style>
