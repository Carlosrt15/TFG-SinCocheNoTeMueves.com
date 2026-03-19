<template>
  <nav>
    <router-link to="/" class="logo">SinCocheNoTeMueves</router-link>

    <div class="links">
      <router-link to="/">Inicio</router-link>
      <router-link to="/vehiculos">Vehículos</router-link>

      <!-- Contador dinámico -->
      <router-link to="/favoritos">
        Favoritos ({{ totalFavoritos }})
      </router-link>

      <router-link to="/contacto">Contacto</router-link>
      <router-link to="/chat">Chat</router-link>

      <!-- Links para usuarios autenticados -->
      <template v-if="logueado">
        <router-link to="/publicar">Vender</router-link>
        <router-link to="/perfil">Perfil</router-link>
        <router-link v-if="esAdmin" to="/admin">Admin</router-link>
      </template>

      <!-- Links para visitantes -->
      <template v-else>
        <router-link to="/login">Login</router-link>
        <router-link to="/registro">Registrarse</router-link>
      </template>
    </div>
  </nav>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const totalFavoritos = ref(0)
const logueado = ref(false)
const esAdmin = ref(false)

/* Cuenta favoritos guardados y estado de sesión */
const actualizarEstado = () => {
  const favoritos = JSON.parse(localStorage.getItem('favoritos')) || []
  totalFavoritos.value = favoritos.length

  const token = localStorage.getItem('token')
  logueado.value = !!token

  const datos = localStorage.getItem('usuario')
  if (datos) {
    const user = JSON.parse(datos)
    esAdmin.value = user.rol === 'admin'
  } else {
    esAdmin.value = false
  }
}

onMounted(() => {
  actualizarEstado()
  window.addEventListener('storage', actualizarEstado)
})

onUnmounted(() => {
  window.removeEventListener('storage', actualizarEstado)
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