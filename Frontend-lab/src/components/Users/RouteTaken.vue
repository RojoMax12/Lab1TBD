<template>
  <div class="route-taken-view">
    <h1 class="title">Rutas Tomadas</h1>

    <div class="container">
      <div class="horizontal-scroll">
        <table class="route-table">
          <thead>
            <tr>
              <th>ID Ruta</th>
              <th>ID Conductor</th>
              <th>Fecha</th>
              <th>Hora Inicio</th>
              <th>Hora Fin</th>
              <th>Estado Ruta</th>
              <th>ID Central</th>
              <th>ID Punto de Retiro</th>
            </tr>
          </thead>

          <tbody>
            <!-- Ruta Ejemplo 1 -->
            <tr>
              <td>1</td>
              <td>12</td>
              <td>2025-11-12</td>
              <td>08:30</td>
              <td>09:10</td>
              <td>Completada</td>
              <td>5</td>
              <td>22</td>
            </tr>

            <!-- Ruta Ejemplo 2 -->
            <tr>
              <td>2</td>
              <td>7</td>
              <td>2025-11-10</td>
              <td>14:00</td>
              <td>14:45</td>
              <td>En progreso</td>
              <td>3</td>
              <td>31</td>
            </tr>

            <!-- Ruta Ejemplo 3 -->
            <tr>
              <td>3</td>
              <td>18</td>
              <td>2025-11-03</td>
              <td>09:15</td>
              <td>10:00</td>
              <td>Cancelada</td>
              <td>2</td>
              <td>16</td>
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
  max-width: 1200px;
  margin: 2rem auto;
  background: #fff;
  border-radius: 1.2rem;
  box-shadow: 0 6px 18px rgba(78,83,54,0.10);
  padding: 2rem 1.2rem;
  font-family: system-ui, -apple-system, "Segoe UI", Roboto, Arial;
}

.title {
  font-size: 1.8rem;
  font-weight: 700;
  color: #4e5336;
  margin-bottom: 1.6rem;
  text-align: center;
}

.horizontal-scroll {
  overflow-x: auto;
  padding-bottom: 10px;
}

.route-table {
  width: 100%;
  min-width: 1000px;
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
  vertical-align: middle;
}

.route-table th {
  background: linear-gradient(180deg,#5e6541,#52563f);
  color: #fff;
  font-weight: 600;
  letter-spacing: 0.2px;
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
    font-size: 0.95rem;
  }
}

.container {
  overflow-x: auto;
}
</style>
