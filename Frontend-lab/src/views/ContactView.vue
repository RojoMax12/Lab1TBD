<template>
  <div>
    <Header />

    <main class="contact-container">
      <!-- Sección de encabezado del formulario de contacto -->
      <section class="contact-hero">
        <h1 class="contact-title">Contáctanos</h1>
        <p class="contact-subtitle">
          Nos encantaría saber de ti. Si tienes alguna pregunta, inquietud o sugerencia, no dudes en ponerte en contacto con nosotros. Estamos aquí para ayudarte.
        </p>
      </section>

      <!-- Formulario de contacto -->
      <section class="contact-form-section">
        <form @submit.prevent="submitForm" class="contact-form">
          <div class="form-group">
            <label for="name" class="form-label">Nombre</label>
            <input id="name" type="text" v-model="form.name" required placeholder="Ingresa tu nombre" class="form-input" />
          </div>

          <div class="form-group">
            <label for="email" class="form-label">Correo Electrónico</label>
            <input id="email" type="email" v-model="form.email" required placeholder="Ingresa tu correo electrónico" class="form-input" />
          </div>

          <div class="form-group">
            <label for="subject" class="form-label">Asunto</label>
            <input id="subject" type="text" v-model="form.subject" required placeholder="Ingresa el asunto" class="form-input" />
          </div>

          <div class="form-group">
            <label for="message" class="form-label">Mensaje</label>
            <textarea id="message" v-model="form.message" required placeholder="Escribe tu mensaje" class="form-input"></textarea>
          </div>

          <button type="submit" class="submit-btn">Enviar</button>
        </form>
      </section>
    </main>

    <Footer />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import Header from '@/components/Home/Header.vue';
import Footer from '@/components/Home/Footer.vue';

const form = ref({
  name: '',
  email: '',
  subject: '',
  message: ''
});

function submitForm() {
  // Aquí puedes agregar la lógica para enviar el formulario, ya sea mediante una API o cualquier otro método.
  console.log('Formulario enviado', form.value);
  alert('¡Gracias por contactarnos!');
  form.value = { name: '', email: '', subject: '', message: '' }; // Limpiar el formulario después de enviarlo
}
</script>

<style scoped>
:root {
  --bg-start: #6a704a;
  --bg-end: #4e5336;
}

.contact-container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 2.5rem 1.5rem;
  font-family: 'Roboto', sans-serif;
}

.contact-hero {
  text-align: center;
  margin-bottom: 3rem;
  background: linear-gradient(180deg, var(--bg-start), var(--bg-end));
  padding: 4rem 2rem;
  border-radius: 8px;
}

.contact-title {
  font-size: 2.8rem;
  font-weight: 700;
  color: #fff;
  margin-bottom: 15px;
}

.contact-subtitle {
  font-size: 1.2rem;
  color: #ecf0f1;
  max-width: 750px;
  margin: 0 auto;
  line-height: 1.6;
  opacity: 0.9;
}

.contact-form-section {
  background-color: #ecf0f1;
  padding: 3rem 2rem;
  border-radius: 8px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-label {
  display: block;
  font-size: 1rem;
  color: #2c3e50;
  margin-bottom: 0.5rem;
  font-weight: 600;
}

.form-input {
  width: 100%;
  padding: 1rem;
  border-radius: 8px;
  border: 1px solid #bdc3c7;
  font-size: 1rem;
  background-color: #fff;
  transition: border 0.3s ease;
}

.form-input:focus {
  border-color: #3498db;
  outline: none;
}

textarea.form-input {
  min-height: 150px;
}

.submit-btn {
  background-color: var(--bg-start);
  color: white;
  padding: 1rem 2rem;
  border-radius: 50px;
  border: none;
  font-size: 1.2rem;
  cursor: pointer;
  width: 100%;
  transition: background-color 0.3s ease;
}

.submit-btn:hover {
  background-color: var(--bg-end);
}

@media (max-width: 768px) {
  .contact-title {
    font-size: 2.2rem;
  }

  .contact-form-section {
    padding: 2.5rem 1.5rem;
  }
}
</style>
