<template>
  <div class="container" v-if="vehiculo">

    <h2>{{ vehiculo.marca }} {{ vehiculo.modelo }}</h2>

    <p><strong>Estado:</strong> {{ vehiculo.estado }}</p>
    <p><strong>Precio:</strong> {{ vehiculo.precio.toLocaleString() }} €</p>

    <p v-if="!vehiculo.disponible" class="vendido">
      🚫 Vehículo vendido 🚫 ;(
    </p>

    <button 
      v-if="vehiculo.disponible"
      @click="confirmarCompra"
      class="comprar"
    >
      Comprar
    </button>

    <p v-if="mensaje" class="exito">{{ mensaje }}</p>

  </div>

  <p v-else>Vehículo no encontrado</p>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const vehiculo = ref(null)
const mensaje = ref('')

onMounted(() => {
  const lista = JSON.parse(localStorage.getItem('vehiculos')) || []
  vehiculo.value = lista.find(v => v.id == route.params.id)
})

const confirmarCompra = () => {

  const confirmar = window.confirm(
    `¿Estás seguro de realizar la compra del ${vehiculo.value.marca} ${vehiculo.value.modelo} por ${vehiculo.value.precio.toLocaleString()} €?`
  )

  if (confirmar) {
    realizarCompra()
  }
}

const realizarCompra = () => {

  const lista = JSON.parse(localStorage.getItem('vehiculos')) || []

  const index = lista.findIndex(v => v.id == vehiculo.value.id)

  if (index !== -1) {
    lista[index].disponible = false
    localStorage.setItem('vehiculos', JSON.stringify(lista))
    vehiculo.value.disponible = false
  }

  mensaje.value = "Compra realizada correctamente "
}
</script>

<style scoped>
.container {
  padding: 40px;
}

.comprar {
  padding: 10px 15px;
  background: green;
  color: white;
  border: none;
  border-radius: 8px;
}

.vendido {
  color: red;
  font-weight: bold;
}

.exito {
  color: green;
  margin-top: 10px;
}
</style>