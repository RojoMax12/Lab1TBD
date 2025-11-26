<template>
  <div>
    <HomeDriverView />
  </div>

  <div class="route-assigned-view">
    <h1 class="title">Rutas Asignadas</h1>

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
                <th>Acciones</th>
            </tr>
          </thead>

          <tbody>
            <tr v-for="driver in drivers" :key="driver.id">
              <td>{{ driver.id }}</td>
              <td>{{ driver.id_driver }}</td>
              <td>{{ driver.date_ }}</td>
              <td>{{ driver.start_time }}</td>
              <td>{{ driver.end_time }}</td>
              <td>{{ driver.route_status }}</td>
              <td>{{ driver.id_central }}</td>
              <td>{{ driver.id_pick_up_point }}</td>
              <td class="row-actions">
                <button class="btn-take" @click="Take(driver)">Tomar</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import HomeDriverView from '@/components/Users/HomeDrive.vue'
import { ref } from 'vue'

const drivers = ref([
  {
    id: 1,
    id_driver: 101,
    date_: '12/11/2025',
    start_time: '09:00',
    end_time: '12:00',
    route_status: 'Asignada',
    id_central: 5,
    id_pick_up_point: 20
  },
  {
    id: 2,
    id_driver: 102,
    date_: '13/11/2025',
    start_time: '10:00',
    end_time: '13:00',
    route_status: 'Asignada',
    id_central: 6,
    id_pick_up_point: 21
  }
])

const Take = (driver) => {
  // Aquí puedes implementar la lógica para "tomar" la ruta o hacer alguna acción.
  console.log("Ruta tomada por el conductor:", driver.id);
}
</script>

<style scoped>
.route-assigned-view {
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

.scrollable-table {
  max-height: 300px; 
  overflow-y: auto;  
}

.btn-take {
  background: #4e5336;
  color: #fff;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 600;
  transition: background 0.18s, box-shadow 0.18s;
}
</style>
