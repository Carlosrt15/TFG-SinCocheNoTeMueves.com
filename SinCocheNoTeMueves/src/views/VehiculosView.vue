<template>
  <div class="container">
    <h2>Catálogo de Vehículos</h2>
    <input v-model="busqueda" placeholder="Buscar por marca..." />
    <VehiculoCard
      v-for="v in filtrados"
      :key="v.id"
      :vehiculo="v"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import api from '../services/api'
import VehiculoCard from '../components/VehiculoCard.vue'

const vehiculos = ref([])
const busqueda = ref('')

onMounted(async () => {
  const res = await api.get('/vehiculos')
  vehiculos.value = res.data
})

const filtrados = computed(() =>
  vehiculos.value.filter(v =>
    v.marca.toLowerCase().includes(busqueda.value.toLowerCase())
  )
)
</script>