<template>
  <div class="card">

    <img :src="vehiculo.imagen" alt="coche" />

    <div class="info">
      <h3>{{ vehiculo.marca }} {{ vehiculo.modelo }}</h3>
      <p>{{ vehiculo.estado }}</p>
      <p class="precio">{{ vehiculo.precio.toLocaleString() }} €</p>

      <button @click="agregarFavorito">
        ⭐ Añadir a favoritos
      </button>
    </div>

  </div>
</template>

<script setup>
import { defineProps } from 'vue'

const props = defineProps(['vehiculo'])

/* Añade vehículo a favoritos */
const agregarFavorito = () => {
  let favoritos = JSON.parse(localStorage.getItem('favoritos')) || []

  const existe = favoritos.find(f => f.id === props.vehiculo.id)

  if (!existe) {
    favoritos.push(props.vehiculo)
    localStorage.setItem('favoritos', JSON.stringify(favoritos))

    // Forzamos actualización del navbar
    window.dispatchEvent(new Event('storage'))

    alert('Añadido a favoritos')
  } else {
    alert('Ya está en favoritos')
  }
}
</script>

<style scoped>
.card {
  background: white;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 8px 20px rgba(0,0,0,0.1);
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

button {
  margin-top: 10px;
  width: 100%;
  padding: 10px;
  background: #0077ff;
  border: none;
  color: white;
  border-radius: 8px;
  cursor: pointer;
}

button:hover {
  background: #005edb;
}
</style>