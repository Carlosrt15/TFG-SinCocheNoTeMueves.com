<template>
  <nav>
    <div class="logo">SinCocheNoTeMueves</div>

    <div class="links">
      <router-link to="/">Inicio</router-link>
      <router-link to="/registro">Registrarse</router-link>
       <router-link to="/login">Login</router-link>
      <router-link to="/vehiculos">Vehículos</router-link>
     
      <!-- Contador dinámico -->
      <router-link to="/favoritos">
        Favoritos ({{ totalFavoritos }})
      </router-link>

      <router-link to="/contacto">Contacto</router-link>
      <router-link to="/chat">Chat</router-link>
      <router-link to="/perfil">Perfil</router-link>
      
      
      <router-link to="/publicar">Vender</router-link>
    </div>
  </nav>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const totalFavoritos = ref(0)

/* Cuenta favoritos guardados */
const actualizarContador = () => {
  const favoritos = JSON.parse(localStorage.getItem('favoritos')) || []
  totalFavoritos.value = favoritos.length
}

onMounted(() => {
  actualizarContador()

  // Escucha cambios
  window.addEventListener('storage', actualizarContador)
})

onUnmounted(() => {
  window.removeEventListener('storage', actualizarContador)
})
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

.links {
  display: flex;
  align-items: center;
}

a {
  color: white;
  margin-left: 20px;
  text-decoration: none;
  transition: 0.2s;
}

a:hover {
  color: #00c3ff;
}

a.router-link-active {
  border-bottom: 2px solid #00c3ff;
}
</style>