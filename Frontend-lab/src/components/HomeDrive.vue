<template>
  <header class="header" role="banner">
    <div class="profile-container">
      <img src="/logo.png" alt="Logo empresa" class="logo" />
      <div class="profile-info">
        <h1 class="name-employeer">Nombre del Conductor</h1>
        <span class="role">Conductor</span>
      </div>
    </div>
    <button class="logout-btn" @click="logout" aria-label="Cerrar sesión">
      <svg width="22" height="22" viewBox="0 0 22 22" fill="none" aria-hidden="true">
        <path d="M7 11h8m0 0l-3-3m3 3l-3 3" stroke="#4d5d39" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        <rect x="2.5" y="2.5" width="17" height="17" rx="5" stroke="#4d5d39" stroke-width="1.5"/>
      </svg>
      <span class="logout-text">Cerrar sesión</span>
    </button>
  </header>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

function logout() {
  // Aquí puedes agregar la lógica de cierre de sesión, como limpiar tokens, etc.
  router.push({ name: 'home' }) // Redirige a la página de inicio después de cerrar sesión
}
</script>

<style scoped>
.header {
  width: 100%;
  background: linear-gradient(180deg, #5e6541, #4d5d39);
  color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.1rem 2rem;
  box-shadow: 0 2px 12px rgba(0,0,0,0.10);
  border-bottom: 2px solid rgba(255,255,255,0.04);
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
}
</style>