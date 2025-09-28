<template>
  <Header />
  <div class="content-wrapper">
    <RouterView />
  </div>
  <Footer />
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'
</script>

<style>
/* Estilos globales */
body, html {
  height: 100%;
  margin: 0;
  padding: 0;
  background-color: #f7e8dd;
}

.content-wrapper {
  flex-grow: 1; 
  margin-top: 80px; /* Espacio para el header fijo */
  background-color: #f7e8dd;
  max-width: 1280px;
  margin-left: auto;
  margin-right: auto;
  padding: 40px;
}
</style>