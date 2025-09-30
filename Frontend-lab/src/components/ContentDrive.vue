<template>
  <div class="container">
    <!-- Mapa -->
    <div class="map-container">
      <img src="/Mapa-ruta.png" alt="Mapa" />
    </div>

    <!-- Panel de información -->
    <div class="info-wrapper">
      <!-- Botón encima del panel -->
      <button class="info-button">Rutas realizadas</button>

      <div class="info-panel">
        <div class="grid">
          <div>Fecha - hora</div>
          <div>Estado ruta</div>

          <div>Nombre central ini</div>
          <div>Nombre central fin</div>

          <div>Coordenada</div>
          <div>Coordenada</div>
        </div>

        <div class="full-width-box">Coordenada del punto siguiente</div>
        <div class="full-width-box">Tipo de residuo</div>
      </div>
    </div>
  </div>
</template>

<script setup>
console.log("ContentDrive cargado");
</script>

<style scoped>
/* Contenedor principal */
.container {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  width: 100%;
  max-width: 1200px;
  margin: 2rem auto;
}

@media (min-width: 768px) {
  .container {
    flex-direction: row;
  }
}

/* Mapa */
.map-container {
  flex: 1;
  background-color: white;
  border-radius: 1rem;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  padding: 1rem;
  display: flex;
  justify-content: center;
  align-items: center;
}

.map-container img {
  max-width: 100%;
  height: auto;
  border-radius: 1rem;
}

/* Wrapper del panel de información (para colocar botón encima) */
.info-wrapper {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  gap: 1rem;
}

/* Botón encima del panel */
.info-button {
  background-color: #5e6541;
  color: white;
  border: none;
  padding: 1.5rem 4.5rem;
  border-radius: 0.75rem;
  cursor: pointer;
  font-weight: bold;
  align-self: center;
}

.info-button:hover {
  background-color: #4a4f37;
}

/* Panel de información */
.info-panel {
  background-color: #e0e0e0;
  border-radius: 1rem;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  padding: 1.5rem;
}

/* Grid de información */
.grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1rem;
}

.grid div {
  background-color: #5e6541;
  color: white;
  text-align: center;
  padding: 0.5rem;
  border-radius: 0.5rem;
}

/* Cajas de ancho completo */
.full-width-box {
  background-color: #5e6541;
  color: white;
  text-align: center;
  padding: 0.5rem;
  border-radius: 0.5rem;
  margin-top: 1rem;
}
</style>
