<template>
  <div class="container">
    <h2>Catálogo de Vehículos</h2>

    <input v-model="busqueda" placeholder="Buscar..." class="buscador" />

    <div class="grid">
      <VehiculoCard v-for="v in filtrados" :key="v.id" :vehiculo="v" />
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";

import VehiculoCard from "../components/VehiculoCard.vue";

import api from "../services/api";

const busqueda = ref("");

const vehiculos = ref([]);

onMounted(async () => {
  try {
    const res = await api.get("/vehiculos");

    vehiculos.value = res.data;
  } catch {
    alert("Error cargando vehículos");
  }
});

const filtrados = computed(() => {
  const texto = busqueda.value.toLowerCase();

  if (!texto) return vehiculos.value;

  return vehiculos.value.filter(
    (v) =>
      v.marca.toLowerCase().includes(texto) ||
      v.modelo.toLowerCase().includes(texto),
  );
});
</script>

<style scoped>
.container {
  padding: 40px 20px;
}

.buscador {
  max-width: 400px;

  display: block;

  margin: auto;

  padding: 12px;

  border-radius: 8px;

  border: 1px solid #ccc;

  margin-bottom: 30px;
}

.grid {
  display: grid;

  grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));

  gap: 30px;
}
</style>
