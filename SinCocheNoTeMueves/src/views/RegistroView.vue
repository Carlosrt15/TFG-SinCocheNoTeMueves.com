<template>
  <div class="auth-page auth-page--registro">
    <div class="auth-left">
      <img src="https://images.unsplash.com/photo-1563720223185-11003d516935?w=800&q=80" alt="Coche de lujo" />
      <div class="auth-left-overlay">
        <div class="auth-left-content">
          <span class="auth-left-badge">🚀 Gratis y sin compromiso</span>
          <h2>Únete a la comunidad</h2>
          <p>Más de 18.000 usuarios ya compran y venden con nosotros cada día.</p>
          <div class="auth-steps">
            <div class="auth-step"><span>1</span> Crea tu cuenta gratis</div>
            <div class="auth-step"><span>2</span> Publica o encuentra tu coche</div>
            <div class="auth-step"><span>3</span> Cierra el trato de forma segura</div>
          </div>
        </div>
      </div>
    </div>

    <div class="auth-right">
      <div class="auth-form-wrap">
        <router-link to="/" class="auth-logo">
          <span>🚗</span>
          <span><span style="color:var(--navy-dark)">SinCoche</span><span style="color:var(--primary)">NoTeMueves</span></span>
        </router-link>

        <div class="auth-header">
          <h1>Crear cuenta</h1>
          <p>¿Ya tienes cuenta? <router-link to="/login">Inicia sesión</router-link></p>
        </div>

        <div v-if="mensajeExito" class="alert alert-success">✅ {{ mensajeExito }}</div>
        <div v-if="mensajeError" class="alert alert-error">⚠️ {{ mensajeError }}</div>

        <form @submit.prevent="registrar" class="registro-form">
          <div class="form-row">
            <div class="form-group">
              <label class="form-label">Nombre</label>
              <input v-model="form.nombre" class="form-input" placeholder="Juan" required />
            </div>
            <div class="form-group">
              <label class="form-label">Apellidos</label>
              <input v-model="form.apellidos" class="form-input" placeholder="García López" required />
            </div>
          </div>

          <div class="form-group">
            <label class="form-label">DNI</label>
            <input v-model="form.dni" class="form-input" placeholder="12345678A" required />
          </div>

          <div class="form-group">
            <label class="form-label">Correo electrónico</label>
            <input v-model="form.email" type="email" class="form-input" placeholder="tu@email.com" required />
          </div>

          <div class="form-group">
            <label class="form-label">Teléfono</label>
            <input v-model="form.telefono" class="form-input" placeholder="600 000 000" required />
          </div>

          <div class="form-row">
            <div class="form-group">
              <label class="form-label">Contraseña</label>
              <input v-model="form.password" type="password" class="form-input" placeholder="••••••••" required />
            </div>
            <div class="form-group">
              <label class="form-label">Confirmar</label>
              <input v-model="confirmPassword" type="password" class="form-input" placeholder="••••••••" required />
            </div>
          </div>

          <button type="submit" class="btn btn-primary btn-full" :disabled="cargando">
            {{ cargando ? 'Creando cuenta...' : 'Crear cuenta gratis →' }}
          </button>
        </form>

        <p class="auth-legal">
          Al registrarte aceptas nuestros <router-link to="/terminos-servicio">Términos de uso</router-link> y
          <router-link to="/politica-privacidad">Política de datos y privacidad</router-link>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import api from '../services/api'

const form = reactive({ nombre: '', apellidos: '', dni: '', email: '', telefono: '', password: '' })
const confirmPassword = ref('')
const mensajeError = ref('')
const mensajeExito = ref('')
const cargando = ref(false)

const registrar = async () => {
  mensajeError.value = ''
  mensajeExito.value = ''
  if (form.password !== confirmPassword.value) {
    mensajeError.value = 'Las contraseñas no coinciden'
    return
  }
  try {
    cargando.value = true
    await api.post('/usuarios/registro', form)
    mensajeExito.value = 'Cuenta creada correctamente. Ya puedes iniciar sesión.'
    Object.keys(form).forEach(key => form[key] = '')
    confirmPassword.value = ''
  } catch {
    mensajeError.value = 'Error al crear la cuenta. Inténtalo de nuevo.'
  } finally {
    cargando.value = false
  }
}
</script>

<style scoped>
.auth-page {
  display: grid;
  grid-template-columns: 1fr 1fr;
  min-height: calc(100vh - var(--navbar-h));
}

.auth-left {
  position: relative;
  overflow: hidden;
}

.auth-left img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.auth-left-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(135deg, rgba(0,50,120,0.88), rgba(0,0,0,0.5));
  display: flex;
  align-items: flex-end;
  padding: 48px;
}

.auth-left-content { color: white; }

.auth-left-badge {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  background: rgba(0,195,255,0.2);
  border: 1px solid rgba(0,195,255,0.4);
  color: var(--accent);
  font-size: 0.8rem;
  font-weight: 600;
  padding: 6px 14px;
  border-radius: var(--radius-pill);
  margin-bottom: 16px;
}

.auth-left-content h2 {
  color: white;
  font-size: 2rem;
  margin-bottom: 10px;
}

.auth-left-content > p {
  color: rgba(255,255,255,0.65);
  line-height: 1.65;
  margin-bottom: 28px;
}

.auth-steps { display: flex; flex-direction: column; gap: 12px; }

.auth-step {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 0.9rem;
  color: rgba(255,255,255,0.8);
}

.auth-step span {
  width: 26px;
  height: 26px;
  border-radius: 50%;
  background: var(--primary);
  color: white;
  font-size: 0.75rem;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.auth-right {
  background: var(--bg-card);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 48px 32px;
  overflow-y: auto;
}

.auth-form-wrap {
  width: 100%;
  max-width: 440px;
}

.auth-logo {
  display: flex;
  align-items: center;
  gap: 8px;
  font-family: var(--font-heading);
  font-size: 1.1rem;
  font-weight: 800;
  text-decoration: none;
  margin-bottom: 36px;
}

.auth-header { margin-bottom: 28px; }

.auth-header h1 { font-size: 1.9rem; margin-bottom: 8px; }

.auth-header p { color: var(--text-muted); font-size: 0.92rem; }

.auth-header a { color: var(--primary); font-weight: 600; text-decoration: none; }
.auth-header a:hover { text-decoration: underline; }

.registro-form { display: flex; flex-direction: column; }

.form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 14px; }

.btn-full { width: 100%; padding: 14px; font-size: 1rem; margin-top: 8px; }

.auth-legal {
  margin-top: 20px;
  font-size: 0.8rem;
  color: var(--text-light);
  text-align: center;
  line-height: 1.6;
}

.auth-legal a { color: var(--primary); }

@media (max-width: 768px) {
  .auth-page { grid-template-columns: 1fr; }
  .auth-left { display: none; }
  .auth-right { padding: 36px 20px; }
  .form-row { grid-template-columns: 1fr; gap: 0; }
}
</style>
