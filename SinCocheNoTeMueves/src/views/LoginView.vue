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
import { useRouter } from 'vue-router'
import api from '../services/api'

const router = useRouter()
const email = ref('')
const password = ref('')

const login = async () => {
  try {
    const res = await api.post('/login', {
      email: email.value,
      password: password.value
    })
    localStorage.setItem('token', res.data.token)

    // Guardar datos del usuario para el perfil
    if (res.data.usuario) {
      localStorage.setItem('usuario', JSON.stringify(res.data.usuario))
    } else {
      // Datos mínimos si el backend no devuelve el objeto completo
      localStorage.setItem('usuario', JSON.stringify({
        nombre: 'Usuario',
        apellidos: '',
        email: email.value,
        dni: '',
        telefono: '',
        rol: 'user'
      }))
    }

    window.dispatchEvent(new Event('storage'))
    router.push('/perfil')
  } catch (error) {
    alert('Error en login')
  }
}
</script>