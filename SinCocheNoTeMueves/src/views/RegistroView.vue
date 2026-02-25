<template>
  <div class="registro-wrapper">
    <div class="registro-card">

      <h2>Crear Cuenta</h2>

     
      <div v-if="mensajeExito" class="mensaje exito">
        {{ mensajeExito }}
      </div>

      <!-- Mensaje de error -->
      <div v-if="mensajeError" class="mensaje error">
        {{ mensajeError }}
      </div>

      <form @submit.prevent="registrar">

        <div class="input-group">
          <input v-model="form.nombre" placeholder="Nombre" required />
          <input v-model="form.apellidos" placeholder="Apellidos" required />
        </div>

        <input v-model="form.dni" placeholder="DNI" required />

        <input v-model="form.email" type="email" placeholder="Correo electrónico" required />

        <input v-model="form.telefono" placeholder="Teléfono" required />

        <input 
          v-model="form.password" 
          type="password" 
          placeholder="Contraseña" 
          required 
        />

        <input 
          v-model="confirmPassword" 
          type="password" 
          placeholder="Confirmar contraseña" 
          required 
        />

        <button type="submit" :disabled="cargando">
          {{ cargando ? "Registrando..." : "Registrarse" }}
        </button>

      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import api from '../services/api'

/*
  Objeto reactivo con los datos del formulario
*/
const form = reactive({
  nombre: '',
  apellidos: '',
  dni: '',
  email: '',
  telefono: '',
  password: ''
})

const confirmPassword = ref('')
const mensajeError = ref('')
const mensajeExito = ref('')
const cargando = ref(false)

/*
  Función de registro
*/
const registrar = async () => {

  mensajeError.value = ''
  mensajeExito.value = ''


  if (form.password !== confirmPassword.value) {
    mensajeError.value = "Las contraseñas no coinciden"
    return
  }

  try {
    cargando.value = true

    /*
      Cuando backend funcionando,
      esto enviará los datos al endpoint /registro
    */
    await api.post('/registro', form)

    mensajeExito.value = "Usuario registrado correctamente"

    // Limpiar formulario
    Object.keys(form).forEach(key => form[key] = '')
    confirmPassword.value = ''

  } catch (error) {
    mensajeError.value = "Error al registrar usuario"
  } finally {
    cargando.value = false
  }
}
</script>

<style scoped>
.registro-wrapper {
  min-height: 80vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #f0f4ff, #e6f0ff);
  padding: 20px;
}

.registro-card {
  background: white;
  padding: 40px;
  border-radius: 16px;
  width: 100%;
  max-width: 500px;
  box-shadow: 0 15px 40px rgba(0,0,0,0.1);
}

h2 {
  text-align: center;
  margin-bottom: 25px;
}

.input-group {
  display: flex;
  gap: 15px;
}

input {
  width: 100%;
  padding: 12px;
  margin-bottom: 15px;
  border-radius: 8px;
  border: 1px solid #ccc;
  transition: 0.3s;
}

input:focus {
  border-color: #0077ff;
  outline: none;
  box-shadow: 0 0 0 2px rgba(0,119,255,0.2);
}

button {
  width: 100%;
  padding: 12px;
  background: #1dd6c7;
  border: none;
  color: white;
  border-radius: 8px;
  cursor: pointer;
  font-weight: bold;
  transition: 0.3s;
}

button:hover {
  background: #3bc43b;
}

button:disabled {
  background: gray;
  cursor: not-allowed;
}

.mensaje {
  padding: 10px;
  margin-bottom: 15px;
  border-radius: 8px;
  text-align: center;
}

.exito {
  background: #d4edda;
  color: #155724;
}

.error {
  background: #f8d7da;
  color: #721c24;
}

@media (max-width: 600px) {
  .input-group {
    flex-direction: column;
  }
}
</style>