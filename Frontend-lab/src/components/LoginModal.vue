<template>
  <div class="overlay">
    <div class="modal">
      <!-- Botón cerrar -->
      <button class="close-btn" @click="$emit('close')">←</button>

      <!-- Icono -->
      <img src="/logo.png" alt="icono login" class="modal-icon" />

      <!-- Formulario -->
      <form @submit.prevent="handleLogin">
        <input type="email" v-model="email" placeholder="Correo" required />
        <div class="password-container">
          <input :type="showPassword ? 'text' : 'password'" v-model="password" placeholder="Contraseña" required />
          <button type="button" @click="showPassword = !showPassword">
            👁️
          </button>
        </div>
        <a href="#" class="forgot-password">¿Olvidaste tu contraseña?</a>
        <div class="divider"></div>
        <button type="submit" class="login-btn">Iniciar sesión</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const email = ref("");
const password = ref("");
const showPassword = ref(false);

function handleLogin() {
  console.log("Login con:", email.value, password.value);
  // aquí puedes llamar a tu API o validar datos
}
</script>

<style scoped>
/* Fondo oscuro */
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 50;
}

/* Ventana modal */
.modal {
  background-color: #5e6541;
  padding: 2rem;
  border-radius: 1rem;
  position: relative;
  width: 90%;
  max-width: 400px;
  color: white;
  text-align: center;
}

/* Botón cerrar */
.close-btn {
  position: absolute;
  top: 1rem;
  right: 1rem;
  background: white;
  border: none;
  border-radius: 50%;
  font-size: 1.2rem;
  cursor: pointer;
}

/* Icono */
.icon {
  font-size: 4rem;
  margin-bottom: 1rem;
}

/* Inputs */
input {
  width: 100%;
  padding: 0.8rem;
  margin: 0.5rem 0;
  border-radius: 2rem;
  border: none;
}

/* Contenedor de contraseña con botón ojo */
.password-container {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.password-container input {
  flex: 1;
}

.password-container button {
  background: transparent;
  border: none;
  cursor: pointer;
  font-size: 1.2rem;
}

/* Botón login */
.login-btn {
  margin-top: 1rem;
  background-color: white;
  color: #5e6541;
  padding: 0.8rem 1.5rem;
  border-radius: 2rem;
  border: none;
  cursor: pointer;
  font-weight: bold;
}

.login-btn:hover {
  background-color: #ddd;
}

.modal-icon {
  width: 60px;
  height: 60px;
  margin: 0 auto 1rem;
  display: block;
}
</style>
