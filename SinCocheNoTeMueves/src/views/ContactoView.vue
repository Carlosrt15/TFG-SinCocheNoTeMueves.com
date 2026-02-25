<template>
  <div class="contacto-wrapper">

    <div class="contacto-card">

      <div class="contacto-info">
        <h2>Contacto</h2>
        <p>¿Tienes alguna duda o sugerencia? Escríbenos y te responderemos lo antes posible.</p>

        <div class="info-item">
          <strong>Email:</strong> info@sincochenotemueves.com
        </div>
        <div class="info-item">
          <strong>Teléfono:</strong> 600 000 000
        </div>
        <div class="info-item">
          <strong>Ubicación:</strong> Salamanca, España
        </div>
      </div>

      <form class="contacto-form" @submit.prevent="enviar">
        <div class="input-group">
          <input v-model="nombre" placeholder="Nombre" required />
          <input v-model="apellidos" placeholder="Apellidos" required />
        </div>

        <input v-model="correo" type="email" placeholder="Correo electrónico" required />
        <input v-model="asunto" placeholder="Asunto" required />

        <textarea v-model="mensaje" placeholder="Escribe tu mensaje..." required></textarea>

        <button type="submit">Enviar mensaje</button>
      </form>

    </div>

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
  try {
    await api.post('/contacto', {
      nombre: nombre.value,
      apellidos: apellidos.value,
      correo: correo.value,
      asunto: asunto.value,
      mensaje: mensaje.value
    })

    alert('Mensaje enviado correctamente')

    nombre.value = ''
    apellidos.value = ''
    correo.value = ''
    asunto.value = ''
    mensaje.value = ''

  } catch (error) {
    alert('Error al enviar el mensaje')
  }
}
</script>

<style scoped>
.contacto-wrapper {
  min-height: 80vh;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px 20px;
  background: linear-gradient(135deg, #f0f4ff, #e6f0ff);
}

.contacto-card {
  background: white;
  display: flex;
  gap: 40px;
  padding: 40px;
  border-radius: 16px;
  box-shadow: 0 15px 40px rgba(0,0,0,0.08);
  max-width: 1000px;
  width: 100%;
  flex-wrap: wrap;
}

.contacto-info {
  flex: 1;
  min-width: 250px;
}

.contacto-info h2 {
  margin-bottom: 15px;
  font-size: 28px;
}

.contacto-info p {
  margin-bottom: 20px;
  color: #555;
}

.info-item {
  margin-bottom: 10px;
  color: #333;
}

.contacto-form {
  flex: 1;
  min-width: 250px;
  display: flex;
  flex-direction: column;
}

.input-group {
  display: flex;
  gap: 15px;
}

input, textarea {
  padding: 12px;
  border-radius: 8px;
  border: 1px solid #ccc;
  margin-bottom: 15px;
  transition: 0.3s;
}

input:focus, textarea:focus {
  border-color: #0077ff;
  outline: none;
  box-shadow: 0 0 0 2px rgba(0,119,255,0.2);
}

textarea {
  min-height: 120px;
  resize: none;
}

button {
  background: #0077ff;
  color: white;
  border: none;
  padding: 12px;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
  transition: 0.3s;
}

button:hover {
  background: #005edb;
}

@media (max-width: 768px) {
  .contacto-card {
    flex-direction: column;
  }

  .input-group {
    flex-direction: column;
  }
}
</style>