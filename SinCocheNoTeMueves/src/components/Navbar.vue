<template>
  <nav>
    <router-link to="/" class="logo"> SinCocheNoTeMueves </router-link>

    <div class="links">
      <router-link to="/"> Inicio </router-link>

      <router-link to="/vehiculos"> Vehículos </router-link>

      <router-link to="/favoritos">
        Favoritos ({{ totalFavoritos }})
      </router-link>

      <router-link to="/contacto"> Contacto </router-link>

      <router-link to="/chat"> Chat </router-link>

      <template v-if="logueado">
        <router-link to="/publicar"> Vender </router-link>

        <router-link to="/perfil"> Perfil </router-link>

        <router-link v-if="esAdmin" to="/admin"> Admin </router-link>
      </template>

      <template v-else>
        <router-link to="/login"> Login </router-link>

        <router-link to="/registro"> Registrarse </router-link>
      </template>
    </div>
  </nav>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";

const totalFavoritos = ref(0);

const logueado = ref(false);

const esAdmin = ref(false);

/* ACTUALIZAR ESTADO */

const actualizarEstado = () => {
  /* FAVORITOS SEGURO */

  const favData = localStorage.getItem("favoritos");

  const favoritos = favData ? JSON.parse(favData) : [];

  totalFavoritos.value = favoritos.length;

  /* LOGIN */

  const token = localStorage.getItem("token");

  logueado.value = !!token;

  /* USUARIO */

  const userData = localStorage.getItem("usuario");

  if (userData) {
    try {
      const user = JSON.parse(userData);

      esAdmin.value = user.rol === "ADMIN";
    } catch {
      esAdmin.value = false;
    }
  } else {
    esAdmin.value = false;
  }
};

/* EVENTOS */

onMounted(() => {
  actualizarEstado();

  window.addEventListener(
    "storage",

    actualizarEstado,
  );
});

onUnmounted(() => {
  window.removeEventListener(
    "storage",

    actualizarEstado,
  );
});
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
