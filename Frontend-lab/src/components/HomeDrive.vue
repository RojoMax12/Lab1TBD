<template>
    <header class ="header">
      <div class="perfile-container">
        <img src="/logo.png" alt="Logo" class="logo" />
        <h1 class="name-employeer">
            Nombre del Conductor
        </h1>
      </div>
      <button class="login-btn">
        Cerrar sesión
      </button>
    </header>
</template>

<style scoped>
.header {
  margin-top: -20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  background-color: #4d5d39;
  color: white;
  
}

.logo {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 1rem;
}

.name-employeer {
  font-size: 1.2rem;
  font-weight: bold;
color: #333;
}

.login-btn {
  padding: 0.5rem 1rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.login-btn:hover {
  background-color: #0056b3;
}

.name-employeer {
    font-size: 1.2rem;
    font-weight: bold;
    color: #333;
}
</style>

