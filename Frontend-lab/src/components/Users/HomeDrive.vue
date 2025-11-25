<template>
  <header class="header" role="banner">
    <div class="profile-container">
      <button class="menu-btn" @click="showSidebar = true" aria-label="Abrir menú">
        <svg width="28" height="28" viewBox="0 0 28 28" fill="none">
          <circle cx="14" cy="14" r="14" fill="#f7f7f7"/>
          <circle cx="9" cy="14" r="2" fill="#4d5d39"/>
          <circle cx="14" cy="14" r="2" fill="#4d5d39"/>
          <circle cx="19" cy="14" r="2" fill="#4d5d39"/>
        </svg>
      </button>
      <img src="/logo.png" alt="Logo empresa" class="logo" />
      <div class="profile-info">
        <h1 class="name-employeer">Nombre del Conductor</h1>
        <span class="role">Conductor</span>
      </div>
    </div>
    <div class="header-actions">
      <button class="logout-btn" @click="logout" aria-label="Cerrar sesión">
        <svg width="22" height="22" viewBox="0 0 22 22" fill="none" aria-hidden="true">
          <path d="M7 11h8m0 0l-3-3m3 3l-3 3" stroke="#4d5d39" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          <rect x="2.5" y="2.5" width="17" height="17" rx="5" stroke="#4d5d39" stroke-width="1.5"/>
        </svg>
        <span class="logout-text">Cerrar sesión</span>
      </button>
    </div>

    <!-- Barra lateral -->
    <aside class="sidebar" v-if="showSidebar">
      <div class="sidebar-header">
        <span class="sidebar-title">Menú</span>
        <button class="close-btn" @click="showSidebar = false" aria-label="Cerrar menú">
          &times;
        </button>
      </div>
      <nav class="sidebar-links">
        <a href="#" class="sidebar-link" @click="home">Inicio </a>
        <a href="#" class="sidebar-link" @click="routeTaken"> Rutas realizadas</a>
        <a href="#" class="sidebar-link">Perfil</a>
      </nav>
    </aside>
    <div v-if="showSidebar" class="sidebar-backdrop" @click="showSidebar = false"></div>
  </header>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const showSidebar = ref(false)

function logout() {
  router.push({ name: 'home' })
}

function home() {
  router.push({ name: 'home-driver' })
}

function routeTaken() {
  router.push({ name: 'route-taken' })
}
</script>

<style scoped>
.header {
  --bg-start: #6a704a;
  --bg-end: #4e5336;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: linear-gradient(180deg, var(--bg-start), var(--bg-end));
  color: white;
  padding: 0.75rem 1.25rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.12);
  border-bottom: 1px solid rgba(255, 255, 255, 0.04);
  position: sticky;
  top: 0;
  z-index: 100;
  backdrop-filter: blur(4px);
}

.profile-container {
  display: flex;
  align-items: center;
  gap: 1.1rem;
}

.logo {
  width: 54px;
  height: 54px;
  border-radius: 12px;
  object-fit: contain;
  box-shadow: 0 4px 12px rgba(0,0,0,0.10);
}

.profile-info {
  display: flex;
  flex-direction: column;
  gap: 0.15rem;
}

.name-employeer {
  font-size: 1.25rem;
  font-weight: 700;
  margin: 0;
  color: #fff;
  letter-spacing: 0.5px;
}

.role {
  font-size: 0.98rem;
  color: #dcd6c8;
  font-weight: 500;
  margin-top: 2px;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 1rem;
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

.logout-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  background: linear-gradient(180deg,#fff,#f7f7f7);
  color: #4d5d39;
  border: none;
  padding: 0.55rem 1.1rem;
  border-radius: 999px;
  cursor: pointer;
  font-weight: 600;
  box-shadow: 0 4px 14px rgba(94,101,65,0.10);
  transition: background 0.18s, box-shadow 0.18s;
  outline: none;
}

.logout-btn:hover,
.logout-btn:focus {
  background: #eaeaea;
  box-shadow: 0 6px 18px rgba(94,101,65,0.14);
}

.logout-text {
  font-size: 1rem;
  font-weight: 600;
  color: #4d5d39;
}

/* Barra lateral */
.sidebar {
  position: fixed;
  top: 0;
  left: 0;
  width: 260px;
  height: 100vh;
  background: linear-gradient(180deg, #5e6541, #4d5d39);
  color: #fff;
  box-shadow: -2px 0 16px rgba(0,0,0,0.13);
  z-index: 1001;
  display: flex;
  flex-direction: column;
  padding: 2rem 1.2rem 1.2rem 1.2rem;
  animation: slideInSidebar 0.2s;
}

@keyframes slideInSidebar {
  from { right: -280px; opacity: 0; }
  to { right: 0; opacity: 1; }
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

@media (max-width: 600px) {
  .header {
    flex-direction: column;
    align-items: flex-start;
    padding: 1rem 1rem;
    gap: 0.8rem;
  }
  .profile-container { gap: 0.7rem; }
  .logo { width: 44px; height: 44px; }
  .name-employeer { font-size: 1.05rem; }
  .logout-btn { padding: 0.45rem 0.8rem; font-size: 0.95rem; }
  .sidebar { width: 90vw; min-width: 220px; }
}
</style>