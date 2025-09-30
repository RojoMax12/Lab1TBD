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
  background: white;
  color: #4d5d39;
  border: none;
  padding: 5px 12px;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
}

.login-btn:hover {
  background-color: #f0f0f0;
}

.name-employeer {
    font-size: 1.2rem;
    font-weight: bold;
    color: #333;
}
</style>

