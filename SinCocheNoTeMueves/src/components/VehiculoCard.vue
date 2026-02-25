<template>
  <div class="card">
    <h3>{{ vehiculo.marca }} {{ vehiculo.modelo }}</h3>
    <p>Estado: {{ vehiculo.estado }}</p>
    <p>Precio: {{ vehiculo.precio }} €</p>
    <router-link :to="'/vehiculos/' + vehiculo.id">
      Ver detalle
    </router-link>
  </div>
</template>

<script setup>
defineProps(['vehiculo'])
</script>

<style scoped>
.card {
  border: 1px solid #ccc;
  padding: 15px;
  border-radius: 8px;
  margin: 10px 0;
}
</style>