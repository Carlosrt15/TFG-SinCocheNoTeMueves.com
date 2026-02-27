<template>
  <div class="container">
    <h2>Catálogo de Vehículos</h2>

    <input 
      v-model="busqueda" 
      placeholder="Buscar por marca o modelo..." 
      class="buscador"
    />

    <div class="grid">
      <VehiculoCard
        v-for="v in filtrados"
        :key="v.id"
        :vehiculo="v"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import VehiculoCard from '../components/VehiculoCard.vue'

const busqueda = ref('')
const vehiculos = ref([])

/*
  Al montar la vista:
  - Si no hay coches en localStorage, los creamos
  - Si ya existen, los usamos
*/
onMounted(() => {
  const guardados = JSON.parse(localStorage.getItem('vehiculos'))

  if (!guardados) {
    const iniciales = [
      {
        id: 1,
        marca: 'Audi',
        modelo: 'A4',
        estado: 'Usado',
        precio: 15000,
        imagen: 'https://images.unsplash.com/photo-1549924231-f129b911e442',
        disponible: true
      },
      {
        id: 2,
        marca: 'BMW',
        modelo: 'X5',
        estado: 'Nuevo',
        precio: 30000,
        imagen: 'https://images.unsplash.com/photo-1555215695-3004980ad54e',
        disponible: true
      },
      {
        id: 3,
        marca: 'Mercedes',
        modelo: 'Clase C',
        estado: 'Usado',
        precio: 22000,
        imagen: 'https://images.unsplash.com/photo-1502877338535-766e1452684a',
        disponible: true
      },
      {
        id: 4,
        marca: 'Audi',
        modelo: 'Q7',
        estado: 'Nuevo',
        precio: 45000,
        imagen: 'https://images.unsplash.com/photo-1494976388531-d1058494cdd8',
        disponible: true
      },
      {
        id: 5,
        marca: 'Fiat',
        modelo: 'Bravo Sport',
        estado: 'Usado',
        precio: 3600,
        imagen: '/src/img/fiat.jpg',
        disponible: true
      }
    ]

    localStorage.setItem('vehiculos', JSON.stringify(iniciales))
    vehiculos.value = iniciales
  } else {
    vehiculos.value = guardados
  }
})

const filtrados = computed(() => {
  const texto = busqueda.value.trim().toLowerCase()

  if (!texto) return vehiculos.value

  return vehiculos.value.filter(v =>
    v.marca.toLowerCase().includes(texto) ||
    v.modelo.toLowerCase().includes(texto)
  )
})
</script>

<style scoped>
.container {
  padding: 40px 20px;
}

.buscador {
  max-width: 400px;
  display: block;
  margin: 0 auto 40px auto;
  padding: 12px;
  border-radius: 8px;
  border: 1px solid #ccc;
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
  gap: 30px;
}
</style>