<template>
  <div class="admin-container">
    <!-- Barra superior -->
    <header class="admin-header">
      <div class="admin-left">
        <!-- Menú hamburguesa -->
        <button class="menu-btn">☰</button>

        <!-- Avatar + nombre -->
        <img src="/logo.png" alt="Admin" class="admin-avatar" />
        <span class="admin-name">Administrador</span>
      </div>

      <!-- Botón cerrar sesión -->
      <button class="logout-btn" @click="cerrarSesion">
        Cerrar sesión
      </button>
    </header>

    <!-- Contenido -->
    <main class="admin-main">
      <h2 class="admin-title">Últimos 6 meses</h2>

      <div class="admin-grid">
        <!-- Cuadro -->
        <div class="admin-box"></div>

        <!-- Círculo + botón -->
        <div class="admin-circle-wrapper">
          <div class="admin-circle"></div>
          <button class="calculate-btn">Calcular eficiencia</button>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

function cerrarSesion() {
  // Aquí limpias la sesión / token según tu lógica
  router.push('/') // vuelve al homepage
}
</script>

<style scoped>
/* Contenedor general */
.admin-container {
  min-height: 100vh;
  background-color: #f4e9da;
}

/* Header */
.admin-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #5f6949;
  padding: 1rem 1.5rem;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
}

.admin-left {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.menu-btn {
  font-size: 1.5rem;
  color: white;
  background: none;
  border: none;
  cursor: pointer;
}

.admin-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: 2px solid white;
}

.admin-name {
  color: white;
  font-weight: bold;
  font-size: 1.2rem;
}

.logout-btn {
  background-color: #f4e9da;
  color: black;
  font-weight: 500;
  padding: 0.5rem 1.25rem;
  border-radius: 25px;
  border: none;
  cursor: pointer;
  transition: background 0.3s ease;
}
.logout-btn:hover {
  background-color: #e1d5c5;
}

/* Main */
.admin-main {
  padding: 2rem;
}

.admin-title {
  color: #000000;
  text-align: center;
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 2.5rem;
}

.admin-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 2rem;
  align-items: center;
}

/* Cuadro */
.admin-box {
  background-color: #5f6949;
  border-radius: 20px;
  width: 100%;
  height: 350px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

/* Círculo */
.admin-circle-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.admin-circle {
  background-color: #5f6949;
  border-radius: 50%;
  width: 350px;
  height: 350px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

.calculate-btn {
  margin-top: 1.5rem;
  background-color: #5f6949;
  color: white;
  padding: 0.75rem 1.5rem;
  border-radius: 25px;
  border: none;
  cursor: pointer;
  font-size: 1rem;
  transition: background 0.3s ease;
}
.calculate-btn:hover {
  background-color: #4c553a;
}
</style>
