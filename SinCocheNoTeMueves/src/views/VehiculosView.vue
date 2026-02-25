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
import { ref, computed } from 'vue'
import VehiculoCard from '../components/VehiculoCard.vue'

const busqueda = ref('')

const vehiculos = ref([
  { id: 1, marca: 'Audi', modelo: 'A4', estado: 'Usado', precio: 15000 },
  { id: 2, marca: 'BMW', modelo: 'X5', estado: 'Nuevo', precio: 30000 },
  { id: 3, marca: 'Mercedes', modelo: 'Clase C', estado: 'Usado', precio: 22000 },
  { id: 4, marca: 'Audi', modelo: 'Q7', estado: 'Nuevo', precio: 45000 }
])

const filtrados = computed(() => {
  const texto = busqueda.value.trim().toLowerCase()

  if (!texto) return vehiculos.value

  return vehiculos.value.filter(v => {
    const marca = v.marca.toLowerCase()
    const modelo = v.modelo.toLowerCase()
    return marca.includes(texto) || modelo.includes(texto)
  })
})
</script>