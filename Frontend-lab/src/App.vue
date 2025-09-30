<template>
  <div class="app-wrapper">
    <RouterView />
  </div>
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'
</script>

<style>
html, body {
  height: 100%;
  margin: 0;
  padding: 0;
  background-color: #f7e8dd;
}

#app, .app-wrapper {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* Que todo ocupe el alto de la pantalla */
  width: 100%;
  background-color: #f7e8dd;
  margin: 0;
  padding: 0;
}

/* Quitamos el max-width y el margin-top que limitaba la vista */
</style>
