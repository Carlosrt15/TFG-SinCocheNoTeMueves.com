<template>
  <div class="auth-page">
    <div class="auth-left">
      <img src="https://images.unsplash.com/photo-1544636331-e26879cd4d9b?w=800&q=80" alt="Coche deportivo" />
      <div class="auth-left-overlay">
        <div class="auth-left-content">
          <span class="auth-left-badge">🔐 Acceso seguro</span>
          <h2>Bienvenido de vuelta</h2>
          <p>Gestiona tus anuncios, favoritos y compras desde un solo lugar.</p>
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
          <h1>Iniciar sesión</h1>
          <p>¿No tienes cuenta? <router-link to="/registro">Regístrate gratis</router-link></p>
        </div>

        <form @submit.prevent="login" class="auth-form">
          <div class="form-group">
            <label class="form-label">Correo electrónico</label>
            <input v-model="email" type="email" class="form-input" placeholder="tu@email.com" required />
          </div>

          <div class="form-group">
            <label class="form-label">Contraseña</label>
            <input v-model="password" type="password" class="form-input" placeholder="••••••••" required />
          </div>

          <div v-if="error" class="alert alert-error">⚠️ {{ error }}</div>

          <button type="submit" class="btn btn-primary btn-full" :disabled="cargando">
            {{ cargando ? 'Entrando...' : 'Entrar →' }}
          </button>
        </form>

        <div class="auth-divider"><span>o continúa con</span></div>

        <div class="auth-social">
          <button class="btn-social">🔵 Google</button>
          <button class="btn-social">⬛ Apple</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../services/api'

const router = useRouter()
const email = ref('')
const password = ref('')
const error = ref('')
const cargando = ref(false)

const login = async () => {
  error.value = ''
  cargando.value = true
  try {
    const res = await api.post('/auth/login', { email: email.value, password: password.value })
    localStorage.setItem('token', res.data.token)
    localStorage.setItem('usuario', JSON.stringify(res.data.usuario))
    router.push('/vehiculos')
  } catch {
    error.value = 'Credenciales incorrectas. Inténtalo de nuevo.'
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

/* Left image panel */
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
  background: linear-gradient(135deg, rgba(0,30,80,0.85), rgba(0,0,0,0.5));
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

.auth-left-content p {
  color: rgba(255,255,255,0.65);
  line-height: 1.65;
  max-width: 300px;
}

/* Right form panel */
.auth-right {
  background: var(--bg-card);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 48px 32px;
}

.auth-form-wrap {
  width: 100%;
  max-width: 400px;
}

.auth-logo {
  display: flex;
  align-items: center;
  gap: 8px;
  font-family: var(--font-heading);
  font-size: 1.1rem;
  font-weight: 800;
  text-decoration: none;
  margin-bottom: 40px;
}

.auth-header { margin-bottom: 32px; }

.auth-header h1 {
  font-size: 1.9rem;
  margin-bottom: 8px;
}

.auth-header p {
  color: var(--text-muted);
  font-size: 0.92rem;
}

.auth-header a {
  color: var(--primary);
  font-weight: 600;
  text-decoration: none;
}

.auth-header a:hover { text-decoration: underline; }

.auth-form { display: flex; flex-direction: column; gap: 0; }

.btn-full { width: 100%; padding: 14px; font-size: 1rem; margin-top: 8px; }

/* Divider */
.auth-divider {
  display: flex;
  align-items: center;
  gap: 16px;
  margin: 28px 0;
  color: var(--text-light);
  font-size: 0.82rem;
}

.auth-divider::before,
.auth-divider::after {
  content: '';
  flex: 1;
  height: 1px;
  background: var(--border);
}

/* Social */
.auth-social { display: grid; grid-template-columns: 1fr 1fr; gap: 12px; }

.btn-social {
  padding: 12px;
  border: 1.5px solid var(--border);
  background: var(--bg-card);
  border-radius: var(--radius-md);
  font-size: 0.88rem;
  font-weight: 600;
  cursor: pointer;
  transition: var(--transition);
  color: var(--text);
}

.btn-social:hover { border-color: var(--primary); background: var(--bg-alt); }

@media (max-width: 768px) {
  .auth-page { grid-template-columns: 1fr; }
  .auth-left { display: none; }
  .auth-right { padding: 40px 24px; }
}
</style>
