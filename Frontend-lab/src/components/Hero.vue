<template>
  <section class="hero">
    <img src="/camion.png" alt="Camión" class="hero-img" />
    <p class="hero-text">
      “Una recolección de residuos más ágil y eficiente significa ciudades más limpias, 
      menos impacto ambiental y un mejor aprovechamiento de los recursos. Así, contribuimos 
      juntos a un entorno urbano ordenado, saludable y sostenible para las próximas generaciones.”
    </p>
  </section>
</template>

<style scoped>
.hero {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 100px;
  padding: 30px;
  text-align: justify;
  flex-wrap: wrap;
}
.hero-img {
  width: 300px;
  border: 4px solid hwb(139 21% 53%);
  border-radius: 10px;
}
.hero-text {
  color: black;
  font-weight: bold;
  max-width: 400px;
  font-size: 16px;
  line-height: 1.5;
}
</style>
