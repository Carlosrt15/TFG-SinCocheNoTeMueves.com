<template>
  <div class="container">
    <h2>Registro</h2>
    <form @submit.prevent="registro">
      <input v-model="nombre" placeholder="Nombre" required />
      <input v-model="apellidos" placeholder="Apellidos" required />
      <input v-model="dni" placeholder="DNI" required />
      <input v-model="telefono" placeholder="Teléfono" required />
      <input v-model="password" type="password" placeholder="Contraseña" required />
      <button>Registrarse</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import api from '../services/api'

const nombre = ref('')
const apellidos = ref('')
const dni = ref('')
const telefono = ref('')
const password = ref('')

const registro = async () => {
  try {
    await api.post('/usuarios', {
      nombre: nombre.value,
      apellidos: apellidos.value,
      dni: dni.value,
      telefono: telefono.value,
      password: password.value
    })
    alert('Usuario registrado')
  } catch {
    alert('Error en registro')
  }
}
</script>