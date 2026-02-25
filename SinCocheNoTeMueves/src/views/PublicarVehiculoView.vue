<template>
  <div class="wrapper">
    <div class="card">
      <h2>Publicar Vehículo</h2>

      <form @submit.prevent="publicar">
        <input v-model="form.marca" placeholder="Marca" required />
        <input v-model="form.modelo" placeholder="Modelo" required />
        <input v-model="form.estado" placeholder="Estado (Nuevo/Usado)" required />
        <input v-model.number="form.precio" type="number" placeholder="Precio" required />

        <button type="submit">Publicar</button>
      </form>

      <p v-if="mensaje" class="exito">{{ mensaje }}</p>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'

const form = reactive({
  marca: '',
  modelo: '',
  estado: '',
  precio: 0
})

const mensaje = ref('')

const publicar = () => {
  /*
    Aquí después irá:
    await api.post('/vehiculos', form)
  */

  console.log("Vehículo publicado:", form)

  mensaje.value = "Vehículo publicado correctamente"

  Object.keys(form).forEach(k => form[k] = '')
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
  box-shadow: 0 10px 30px rgba(0,0,0,0.1);
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
  border-radius: 8px;
}
.exito {
  color: green;
  margin-top: 10px;
}
</style>