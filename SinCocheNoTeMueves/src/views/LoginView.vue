<template>
  <div class="container">
    <h2>Iniciar Sesión</h2>
    <form @submit.prevent="login">
      <input v-model="email" placeholder="Email" required />
      <input v-model="password" type="password" placeholder="Contraseña" required />
      <button>Entrar</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import api from '../services/api'

const email = ref('')
const password = ref('')

const login = async () => {
  try {
    const res = await api.post('/login', {
      email: email.value,
      password: password.value
    })
    localStorage.setItem('token', res.data.token)
    alert('Login correcto')
  } catch (error) {
    alert('Error en login')
  }
}
</script>