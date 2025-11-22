<template>
  <div class="admin-container">

    <!-- Header -->
    <HeaderAdmin/>

    <!-- Contenido -->
    <main class="admin-main">
      <h2 class="admin-title">Últimos 6 meses</h2>
      <div class="admin-grid">
        <div class="admin-box"></div>
        <div class="admin-circle-wrapper">
          <div class="admin-circle"></div>
          <button class="calculate-btn">Calcular eficiencia</button>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import HeaderAdmin from '@/components/Admin/HeaderAdmin.vue'

const router = useRouter()
const showSidebar = ref(false)

function cerrarSesion() {
  router.push('/') // vuelve al homepage
}

function users() {
  router.push({ name: 'users' })
}
</script>

<style scoped>
.admin-container {
  min-height: 100vh;
  background-color: #f4e9da;
  position: relative;
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
  background: none;
  border: none;
  cursor: pointer;
  padding: 0.2rem;
  border-radius: 50%;
  transition: background 0.18s;
  display: flex;
  align-items: center;
}
.menu-btn:hover {
  background: #eaeaea;
}

.admin-avatar {
  width: 40px;
  height: 40px;
  object-fit: contain;
  box-shadow: 0 2px 6px rgba(0,0,0,0.10);
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

/* Barra lateral */
.sidebar {
  position: fixed;
  top: 0;
  left: 0;
  width: 240px;
  height: 100vh;
  background: linear-gradient(180deg, #5f6949, #4c553a);
  color: #fff;
  box-shadow: 2px 0 16px rgba(0,0,0,0.13);
  z-index: 1001;
  display: flex;
  flex-direction: column;
  padding: 2rem 1.2rem 1.2rem 1.2rem;
  animation: slideInSidebarLeft 0.2s;
}

@keyframes slideInSidebarLeft {
  from { left: -260px; opacity: 0; }
  to { left: 0; opacity: 1; }
}

.sidebar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.sidebar-title {
  font-size: 1.2rem;
  font-weight: 700;
}

.close-btn {
  background: none;
  border: none;
  font-size: 2rem;
  color: #fff;
  cursor: pointer;
  line-height: 1;
}

.sidebar-links {
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
}

.sidebar-link {
  color: #fff;
  text-decoration: none;
  font-size: 1.08rem;
  font-weight: 500;
  padding: 0.6rem 1rem;
  border-radius: 8px;
  transition: background 0.18s;
}

.sidebar-link:hover {
  background: rgba(255,255,255,0.12);
}

.sidebar-backdrop {
  position: fixed;
  top: 0; left: 0;
  width: 100vw; height: 100vh;
  background: rgba(0,0,0,0.18);
  z-index: 1000;
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

.admin-box {
  background-color: #5f6949;
  border-radius: 20px;
  width: 100%;
  height: 350px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

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

@media (max-width: 900px) {
  .admin-main {
    padding: 1rem;
  }
  .admin-grid {
    grid-template-columns: 1fr;
    gap: 1.2rem;
  }
  .admin-box, .admin-circle {
    height: 220px;
    width: 100%;
    max-width: 320px;
    margin: 0 auto;
  }
  .sidebar {
    width: 80vw;
    min-width: 180px;
  }
}
</style>