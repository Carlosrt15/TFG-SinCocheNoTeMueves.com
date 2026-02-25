<template>
  <div class="container">
    <h2>Contacto</h2>
    <form @submit.prevent="enviar">
      <input v-model="nombre" placeholder="Nombre" required />
      <input v-model="apellidos" placeholder="Apellidos" required />
      <input v-model="correo" type="email" placeholder="Correo" required />
      <input v-model="asunto" placeholder="Asunto" required />
      <textarea v-model="mensaje" placeholder="Mensaje" required></textarea>
      <button>Enviar</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import api from '../services/api'

const nombre = ref('')
const apellidos = ref('')
const correo = ref('')
const asunto = ref('')
const mensaje = ref('')

const enviar = async () => {
  await api.post('/contacto', {
    nombre: nombre.value,
    apellidos: apellidos.value,
    correo: correo.value,
    asunto: asunto.value,
    mensaje: mensaje.value
  })
  alert('Mensaje enviado')
}
</script>