<template>
  <section class="categories">
    <div class="content-wrapper">
      <div class="categories-grid">
        <div class="category-image-container">
          <img src="/residuos.png" alt="Tipos de residuos" />
        </div>
      </div>
      <div class="text-container">
        <h2>Nos encargamos de todo tipo de residuos</h2>
      </div>
    </div>
  </section>
</template>


<style scoped>
.categories {
  color: black;
  font-weight: bold;
  text-align: center;
  padding: 20px;
}

.content-wrapper {
  display: flex; /* Convierte el contenedor en un flexbox */
  align-items: center; /* Centra los elementos verticalmente */
  justify-content: center; /* Centra el contenido horizontalmente */
  gap: 100px; /* Crea el espacio de separación entre la imagen y el texto */
  max-width: 1200px; /* Define un ancho máximo para el contenedor */
  margin: 0 auto; /* Centra el contenedor principal */
}

.text-container {
  /* Estilos para el contenedor del texto */
  width: 300px; /* Limita el ancho del texto para evitar que se estire demasiado */
}

h2 {
  font-size: 1.8em;
  margin-top: 0;
  color: black;
  text-align: left; /* Alinea el texto a la izquierda, como en el mockup */
}

.category-image-container img {
  width: 120%;
  height: auto;
  display: block;
}
</style>