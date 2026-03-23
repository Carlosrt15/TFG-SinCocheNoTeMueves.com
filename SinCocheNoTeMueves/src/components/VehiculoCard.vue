<template>
  <div class="card">
    <img :src="vehiculo.imagen" alt="coche" />

    <div class="info">
      <h3>{{ vehiculo.marca }} {{ vehiculo.modelo }}</h3>
      <p>{{ vehiculo.estado }}</p>
      <p class="precio">{{ vehiculo.precio.toLocaleString() }} €</p>

      <div class="botones">
        <!-- Botón Ver Detalle -->
        <router-link :to="'/vehiculo/' + vehiculo.id">
          <button class="detalle">Ver detalle</button>
        </router-link>

        <!-- Botón Añadir Favorito -->
        <button class="favorito" @click="agregarFavorito">⭐ Fav</button>
      </div>
    </div>
  </div>
</template>

<script setup>
//Dejamos de usar datos falsos y usamos appBack
import { defineProps } from "vue";

import api from "../services/api";

const props = defineProps(["vehiculo"]);

const agregarFavorito = async () => {
  try {
    await api.post("/favoritos/" + props.vehiculo.id);

    alert("Añadido a favoritos");
  } catch {
    alert("Debes iniciar sesión");
  }
};
</script>

<style scoped>
.card {
  background: white;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  transition: 0.3s;
}

.card:hover {
  transform: translateY(-5px);
}

img {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.info {
  padding: 20px;
}

.precio {
  font-weight: bold;
  color: #0077ff;
}

.botones {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

.detalle {
  flex: 1;
  padding: 10px;
  background: #0077ff;
  border: none;
  color: white;
  border-radius: 8px;
}

.favorito {
  width: 50px;
  background: gold;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.favorito:hover {
  background: orange;
}
</style>
