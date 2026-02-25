<template>
  <div class="container">

    <div class="card">
      <h2>{{ vehiculo.marca }} {{ vehiculo.modelo }}</h2>

      <p><strong>Estado:</strong> {{ vehiculo.estado }}</p>
      <p><strong>Precio:</strong> {{ vehiculo.precio.toLocaleString() }} €</p>

      <p v-if="!vehiculo.disponible" class="vendido">
         Este vehículo ya ha sido vendido 
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

  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const mensaje = ref('')

const vehiculo = reactive({
  id: route.params.id,
  marca: 'Audi',
  modelo: 'A4',
  estado: 'Usado',
  precio: 15000,
  disponible: true
})

/*
  Muestra confirmación de seguridad
*/
const confirmarCompra = () => {

  const confirmar = window.confirm(
    `¿Estás seguro de realizar la compra del ${vehiculo.marca} ${vehiculo.modelo} por ${vehiculo.precio.toLocaleString()} €?`
  )

  if (confirmar) {
    realizarCompra()
  }
}

/*
  Simulación de compra
*/
const realizarCompra = () => {

  let compras = JSON.parse(localStorage.getItem('compras')) || []

  compras.push({
    vehiculoId: vehiculo.id,
    nombre: vehiculo.marca + " " + vehiculo.modelo,
    precio: vehiculo.precio,
    fecha: new Date()
  })

  localStorage.setItem('compras', JSON.stringify(compras))

  vehiculo.disponible = false

  mensaje.value = "Compra realizada correctamente :)"
}
</script>

<style scoped>
.container {
  padding: 40px;
  display: flex;
  justify-content: center;
}

.card {
  background: white;
  padding: 30px;
  border-radius: 15px;
  width: 400px;
  box-shadow: 0 10px 25px rgba(0,0,0,0.1);
}

.comprar {
  margin-top: 15px;
  padding: 10px 15px;
  background: green;
  color: white;
  border: none;
  border-radius: 8px;
  width: 100%;
}

.vendido {
  color: red;
  font-weight: bold;
}

.exito {
  color: green;
  margin-top: 15px;
}
</style>