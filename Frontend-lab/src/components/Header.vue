<template>
  <header class="header">
    <div class="logo-container">
      <img src="/logo.png" alt="Logo" class="logo" />
      <h1 class="site-title">Trash TBD</h1>   
    </div>
    <button class="login-btn">Iniciar sesión</button>
  </header>
</template>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  background-color: #4d5d39;
  color: white;
}
.logo-container {
  display: flex;
  align-items: center;
  gap: 10px;
}
.logo {
  width: 80px;
  height: 50px;
}

.site-title {
  font-size: 1.8rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 2px;
  margin: 0;
  background: linear-gradient(135deg, #ffffff, #e8f5e8);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-shadow: 0 2px 4px rgba(0,0,0,0.3);
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
</style>
