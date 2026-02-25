<template>
  <nav>
    <div class="logo">SinCocheNoTeMueves</div>
    <div class="links">
      <router-link to="/">Inicio</router-link>
      <router-link to="/vehiculos">Vehículos</router-link>
      <router-link to="/favoritos">Favoritos</router-link>
      <router-link to="/contacto">Contacto</router-link>
      <router-link to="/chat">Chat</router-link>
      <router-link to="/perfil">Perfil</router-link>
      <router-link to="/login">Login</router-link>
    </div>
  </nav>
</template>

<script setup>
</script>

<style scoped>
nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #1e293b;
  padding: 15px 40px;
}

.logo {
  color: white;
  font-weight: bold;
  font-size: 20px;
}

a {
  color: white;
  margin-left: 20px;
  text-decoration: none;
}

a.router-link-active {
  border-bottom: 2px solid #00c3ff;
}
</style>