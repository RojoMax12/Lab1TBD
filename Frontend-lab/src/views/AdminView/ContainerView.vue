<template>
  <div>
    <HeaderAdmin />
  </div>

  <div class="container-wrapper">

    <h1 class="title">Información de Contenedores</h1>

    <!-- BOTONES SUPERIORES -->
    <div class="actions-top">
      <button class="btn-add">Agregar contenedor</button>
      <button class="btn-secondary">Recolección reciente</button>
    </div>

    <!-- TABLA HEADER -->
    <div class="grid-header">
      <div>ID</div>
      <div>ID Waste</div>
      <div>Coord X</div>
      <div>Coord Y</div>
      <div>Peso</div>
      <div>Estado</div>
      <div>Acciones</div>
    </div>

    <!-- EJEMPLO FILA -->
    <div class="grid-row">
      <div>1</div>
      <div>10</div>
      <div>-33.4500</div>
      <div>-70.6600</div>
      <div>120 kg</div>
      <div>Lleno</div>

      <div class="row-actions">
        <button class="btn-edit">Editar</button>
        <button class="btn-delete">Eliminar</button>
      </div>
    </div>

  </div>
</template>

<script setup lang="ts">
import HeaderAdmin from '@/components/Admin/HeaderAdmin.vue'
</script>

<style scoped>
/* Contenedor principal */
.container-wrapper {
  background-color: #f9f9f9;
  padding: 25px;
  border-radius: 12px;
  max-width: 1000px;
  margin: 40px auto;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

/* Título */
.title {
  text-align: center;
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 25px;
  color: #4a4f37;
}

/* Botones superiores */
.actions-top {
  display: flex;
  justify-content: flex-end;
  gap: 12px;        /* ← separación entre botones */
  margin-bottom: 20px;
}

/* Botón principal */
.btn-add {
  padding: 10px 18px;
  background: #4a4f37;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 500;
}

.btn-add:hover {
  background: #3c4030;
}

/* Botón secundario */
.btn-secondary {
  padding: 10px 18px;
  background: #4a4f37;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 500;
}

.btn-secondary:hover {
  background: #686f54;
}

/* Tabla header */
.grid-header {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  background: #52563f;
  color: white;
  padding: 12px;
  border-radius: 6px;
  font-weight: bold;
  text-align: center;
}

/* Tabla filas */
.grid-row {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  padding: 12px;
  align-items: center;
  border-bottom: 1px solid #ddd;
  text-align: center;
  background: white;
}

/* Acciones */
.row-actions {
  display: flex;
  justify-content: center;
  gap: 10px;    /* ← más espacio entre botones */
}

.btn-edit {
  background: #ecd674;
  border: none;
  padding: 6px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.btn-delete {
  background: #d1655d;
  border: none;
  padding: 6px 10px;
  border-radius: 5px;
  color: white;
  cursor: pointer;
}
</style>
