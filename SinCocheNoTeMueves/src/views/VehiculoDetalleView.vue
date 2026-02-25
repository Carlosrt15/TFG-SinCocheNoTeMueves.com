<template>
  <div class="container">
    <h2>Detalle del Vehículo</h2>

    <div v-if="vehiculo">
      <p><strong>Marca:</strong> {{ vehiculo.marca }}</p>
      <p><strong>Modelo:</strong> {{ vehiculo.modelo }}</p>
      <p><strong>Estado:</strong> {{ vehiculo.estado }}</p>
      <p><strong>Precio:</strong> {{ vehiculo.precio }} €</p>
    </div>

    <div v-else>
      <p>Vehículo no encontrado</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const vehiculo = ref({
  id: route.params.id,
  marca: 'BMW',
  modelo: 'X5',
  estado: 'Nuevo',
  precio: 30000
})
</script>

<style scoped>
.container {
  padding: 30px;
}
</style>