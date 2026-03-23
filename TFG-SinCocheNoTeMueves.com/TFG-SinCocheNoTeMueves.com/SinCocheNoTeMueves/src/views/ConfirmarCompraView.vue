<template>
  <div class="wrapper">
    <div class="card">
      <h2>Confirmar Compra</h2>

      <p>¿Estás seguro que deseas comprar este vehículo?</p>

      <button @click="confirmar">Confirmar</button>
      <router-link to="/vehiculos">
        <button class="cancelar">Cancelar</button>
      </router-link>

      <p v-if="mensaje" class="exito">{{ mensaje }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const mensaje = ref('')

const confirmar = () => {
  /*
    Aquí irá:
    await api.post('/transacciones')
  */
  mensaje.value = "Compra realizada correctamente"
}
</script>

<style scoped>
.wrapper {
  display: flex;
  justify-content: center;
  padding: 50px;
}
.card {
  background: white;
  padding: 40px;
  border-radius: 15px;
  width: 400px;
  text-align: center;
}
button {
  margin: 10px;
  padding: 10px 15px;
}
.cancelar {
  background: gray;
  color: white;
}
.exito {
  color: green;
}
</style>