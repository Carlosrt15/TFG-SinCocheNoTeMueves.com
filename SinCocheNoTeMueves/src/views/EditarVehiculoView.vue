<template>
  <div class="wrapper">
    <div class="card">
      <h2>Editar Vehículo</h2>

      <form @submit.prevent="guardar">
        <input v-model="vehiculo.marca" />
        <input v-model="vehiculo.modelo" />
        <input v-model="vehiculo.estado" />
        <input v-model.number="vehiculo.precio" type="number" />

        <button type="submit">Guardar cambios</button>
      </form>

      <p v-if="mensaje" class="exito">{{ mensaje }}</p>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { reactive, ref } from 'vue'

const route = useRoute()

const vehiculo = reactive({
  id: route.params.id,
  marca: 'Audi',
  modelo: 'A4',
  estado: 'Usado',
  precio: 15000
})

const mensaje = ref('')

const guardar = () => {
  /*
    Aquí irá:
    await api.put('/vehiculos/' + vehiculo.id, vehiculo)
  */
  mensaje.value = "Cambios guardados correctamente"
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
}
input {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
}
button {
  width: 100%;
  padding: 12px;
  background: #0077ff;
  color: white;
  border: none;
}
.exito {
  color: green;
}
</style>