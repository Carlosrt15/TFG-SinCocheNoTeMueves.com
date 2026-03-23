<template>
  <div class="container">
    <h2>Mis Favoritos ⭐</h2>

    <div v-if="favoritos.length > 0" class="grid">
      <div v-for="f in favoritos" :key="f.id" class="card">
        <img :src="f.imagen" alt="coche" />

        <h3>{{ f.marca }} {{ f.modelo }}</h3>
        <p>{{ f.precio.toLocaleString() }} €</p>

        <button @click="eliminar(f.id)">
          ❌ Eliminar
        </button>
      </div>
    </div>

    <p v-else>No tienes vehículos en favoritos.</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const favoritos = ref([])

/* Carga favoritos al entrar en la página */
const cargarFavoritos = () => {
  favoritos.value = JSON.parse(localStorage.getItem('favoritos')) || []
}

onMounted(() => {
  cargarFavoritos()
})

/* Elimina favorito */
const eliminar = (id) => {
  favoritos.value = favoritos.value.filter(f => f.id !== id)
  localStorage.setItem('favoritos', JSON.stringify(favoritos.value))

  // Actualiza navbar
  window.dispatchEvent(new Event('storage'))
}
</script>

<style scoped>
.container {
  padding: 40px;
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 25px;
}

.card {
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 6px 18px rgba(0,0,0,0.1);
}

img {
  width: 100%;
  height: 180px;
  object-fit: cover;
  border-radius: 10px;
  margin-bottom: 10px;
}

button {
  width: 100%;
  padding: 10px;
  background: crimson;
  border: none;
  color: white;
  border-radius: 8px;
  cursor: pointer;
}

button:hover {
  background: darkred;
}
</style>