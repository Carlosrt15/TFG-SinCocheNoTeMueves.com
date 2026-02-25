<template>
  <div class="container">
    <h2>{{ vehiculo.marca }} {{ vehiculo.modelo }}</h2>
    <p>Estado: {{ vehiculo.estado }}</p>
    <p>Precio: {{ vehiculo.precio }} €</p>

    <router-link :to="'/comprar/' + vehiculo.id">
      <button class="comprar">Comprar</button>
    </router-link>

    <router-link :to="'/editar/' + vehiculo.id">
      <button class="editar">Editar</button>
    </router-link>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'

const route = useRoute()

const vehiculo = {
  id: route.params.id,
  marca: 'Audi',
  modelo: 'A4',
  estado: 'Usado',
  precio: 15000
}
</script>

<style scoped>
.container {
  padding: 40px;
}
button {
  margin-right: 10px;
  padding: 10px 15px;
}
.comprar {
  background: green;
  color: white;
}
.editar {
  background: orange;
  color: white;
}
</style>