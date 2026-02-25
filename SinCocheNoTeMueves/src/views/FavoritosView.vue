<template>
  <div class="container">
    <h2>Mis Favoritos</h2>

    <div v-if="favoritos.length > 0">
      <div v-for="f in favoritos" :key="f.id" class="card">
        <p><strong>{{ f.marca }} {{ f.modelo }}</strong></p>
        <p>Precio: {{ f.precio }} €</p>
      </div>
    </div>

    <div v-else>
      <p>No tienes vehículos en favoritos.</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const favoritos = ref([
  { id: 1, marca: 'Audi', modelo: 'A4', precio: 15000 },
  { id: 2, marca: 'BMW', modelo: 'X5', precio: 30000 }
])
</script>

<style scoped>
.container {
  padding: 30px;
}

.card {
  border: 1px solid #ccc;
  padding: 15px;
  border-radius: 8px;
  margin: 10px 0;
}
</style>