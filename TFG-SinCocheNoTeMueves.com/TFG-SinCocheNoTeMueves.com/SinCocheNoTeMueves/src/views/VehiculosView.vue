<template>
  <div class="container">
    <h2>Catálogo de Vehículos</h2>

    <input v-model="busqueda" placeholder="Buscar..." class="buscador" />

    <div class="grid">
      <div v-for="v in filtrados" :key="v.id">
        <VehiculoCard :vehiculo="v" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";

import VehiculoCard from "../components/VehiculoCard.vue";

import api from "../services/api";

const busqueda = ref("");

const vehiculos = ref([]);

/* CARGAR VEHICULOS */

onMounted(async () => {
  try {
    const res = await api.get("/vehiculos");

    console.log(res.data); // IMPORTANTE DEBUG

    vehiculos.value = res.data;
  } catch (error) {
    console.log(error);

    alert("Error cargando coches");
  }
});

/* FILTRO */

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
  padding: 40px;
}

.grid {
  display: grid;

  grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));

  gap: 25px;
}

.buscador {
  display: block;

  margin: auto;

  padding: 10px;

  width: 300px;

  margin-bottom: 30px;
}
</style>
