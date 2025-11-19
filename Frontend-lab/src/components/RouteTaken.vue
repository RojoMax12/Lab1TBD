<template>
  <div class="route-taken-view">
    <h1 class="title">Rutas Tomadas</h1>
    <div class="container">
      <div class="horizontal-scroll">
        <table class="route-table">
          <thead>
            <tr>
              <th>Distancia Total</th>
              <th>Duración</th>
              <th>Velocidad Promedio</th>
              <th>Calorías Quemadas</th>
              <th>Descripción de la Ruta</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>12.5 km</td>
              <td>25 min</td>
              <td>30 km/h</td>
              <td>200 kcal</td>
              <td>Esta ruta te llevará a través de hermosos paisajes urbanos y parques locales, ideal para una caminata relajante o un paseo en bicicleta.</td>
            </tr>
            <!-- Ejemplo de otra ruta -->
            <tr>
              <td>8.2 km</td>
              <td>18 min</td>
              <td>27 km/h</td>
              <td>150 kcal</td>
              <td>Ruta corta por el centro de la ciudad y plazas principales.</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.route-taken-view {
  width: 100%;
  max-width: 1100px;
  margin: 2rem auto;
  background: #fff;
  border-radius: 1.2rem;
  box-shadow: 0 6px 18px rgba(78,83,54,0.10);
  padding: 2rem 1.2rem;
  font-family: system-ui, -apple-system, "Segoe UI", Roboto, Arial;
}

.title {
  font-size: 1.5rem;
  font-weight: 700;
  color: #4e5336;
  margin-bottom: 1.2rem;
  text-align: center;
}

.container {
  width: 100%;
}

.horizontal-scroll {
  overflow-x: auto;
  padding-bottom: 8px;
}

.route-table {
  width: 100%;
  min-width: 800px;
  border-collapse: separate;
  border-spacing: 0;
  background: #fff;
  border-radius: 1rem;
  box-shadow: 0 2px 8px rgba(78,83,54,0.08);
}

.route-table th, .route-table td {
  padding: 1rem 1.2rem;
  text-align: left;
  font-size: 1rem;
  border-bottom: 1px solid #eaeaea;
  vertical-align: top;
}

.route-table th {
  background: linear-gradient(180deg,#5e6541,#52563f);
  color: #fff;
  font-weight: 600;
  letter-spacing: 0.2px;
  border-top-left-radius: 1rem;
  border-top-right-radius: 1rem;
}

.route-table td {
  color: #4e5336;
  font-weight: 500;
  background: #f7f7f7;
}

.route-table tr:last-child td {
  border-bottom: none;
}

@media (max-width: 900px) {
  .route-taken-view {
    max-width: 98vw;
    padding: 1.2rem 0.5rem;
  }
  .route-table th, .route-table td {
    padding: 0.7rem 0.7rem;
    font-size: 0.97rem;
  }
}
</style>