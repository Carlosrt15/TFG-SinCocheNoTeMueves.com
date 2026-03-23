<template>
  <nav class="navbar" :class="{ 'navbar--scrolled': scrolled, 'navbar--open': menuOpen }">
    <div class="navbar-inner">

      <!-- Logo -->
      <router-link to="/" class="navbar-logo" @click="menuOpen = false">
        <span class="logo-icon">🚗</span>
        <span class="logo-text">
          <span class="logo-main">SinCoche</span><span class="logo-accent">NoTeMueves</span>
        </span>
      </router-link>

      <!-- Desktop Links -->
      <div class="navbar-links">
        <router-link to="/" class="nav-link">Inicio</router-link>
        <router-link to="/vehiculos" class="nav-link">Vehículos</router-link>
        <router-link to="/favoritos" class="nav-link">
          Favoritos
          <span v-if="totalFavoritos > 0" class="nav-badge">{{ totalFavoritos }}</span>
        </router-link>
        <router-link to="/contacto" class="nav-link">Contacto</router-link>
        <router-link to="/chat" class="nav-link">Chat</router-link>

        <template v-if="logueado">
          <router-link to="/publicar" class="nav-link nav-link--cta">
            + Vender
          </router-link>
          <router-link to="/perfil" class="nav-link">Perfil</router-link>
          <router-link v-if="esAdmin" to="/admin" class="nav-link nav-link--admin">Admin</router-link>
        </template>

        <template v-else>
          <router-link to="/login" class="nav-link">Login</router-link>
          <router-link to="/registro" class="nav-btn">Registrarse</router-link>
        </template>
      </div>

      <!-- Mobile Hamburger -->
      <button class="hamburger" @click="menuOpen = !menuOpen" aria-label="Menú">
        <span></span><span></span><span></span>
      </button>
    </div>

    <!-- Mobile Menu -->
    <div class="mobile-menu" v-show="menuOpen">
      <router-link to="/" class="mobile-link" @click="menuOpen = false">Inicio</router-link>
      <router-link to="/vehiculos" class="mobile-link" @click="menuOpen = false">Vehículos</router-link>
      <router-link to="/favoritos" class="mobile-link" @click="menuOpen = false">Favoritos ({{ totalFavoritos }})</router-link>
      <router-link to="/contacto" class="mobile-link" @click="menuOpen = false">Contacto</router-link>
      <router-link to="/chat" class="mobile-link" @click="menuOpen = false">Chat</router-link>

      <template v-if="logueado">
        <router-link to="/publicar" class="mobile-link mobile-link--cta" @click="menuOpen = false">+ Vender vehículo</router-link>
        <router-link to="/perfil" class="mobile-link" @click="menuOpen = false">Mi perfil</router-link>
        <router-link v-if="esAdmin" to="/admin" class="mobile-link" @click="menuOpen = false">Panel Admin</router-link>
      </template>
      <template v-else>
        <router-link to="/login" class="mobile-link" @click="menuOpen = false">Iniciar sesión</router-link>
        <router-link to="/registro" class="mobile-link mobile-link--cta" @click="menuOpen = false">Crear cuenta</router-link>
      </template>
    </div>
  </nav>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const totalFavoritos = ref(0)
const logueado = ref(false)
const esAdmin = ref(false)
const scrolled = ref(false)
const menuOpen = ref(false)

const actualizarEstado = () => {
  const favData = localStorage.getItem('favoritos')
  const favoritos = favData ? JSON.parse(favData) : []
  totalFavoritos.value = favoritos.length
  const token = localStorage.getItem('token')
  logueado.value = !!token
  const userData = localStorage.getItem('usuario')
  if (userData) {
    try {
      const user = JSON.parse(userData)
      esAdmin.value = user.rol === 'ADMIN'
    } catch { esAdmin.value = false }
  } else { esAdmin.value = false }
}

const onScroll = () => { scrolled.value = window.scrollY > 20 }

onMounted(() => {
  actualizarEstado()
  window.addEventListener('storage', actualizarEstado)
  window.addEventListener('scroll', onScroll)
})

onUnmounted(() => {
  window.removeEventListener('storage', actualizarEstado)
  window.removeEventListener('scroll', onScroll)
})
</script>

<style scoped>
.navbar {
  position: sticky;
  top: 0;
  z-index: 1000;
  background: var(--navy-dark);
  border-bottom: 1px solid rgba(255,255,255,0.06);
  transition: var(--transition);
}

.navbar--scrolled {
  background: rgba(15,23,42,0.96);
  backdrop-filter: blur(12px);
  box-shadow: 0 4px 24px rgba(0,0,0,0.25);
}

.navbar-inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: var(--navbar-h);
  width: 92%;
  max-width: 1160px;
  margin: 0 auto;
  padding: 0 16px;
}

/* Logo */
.navbar-logo {
  display: flex;
  align-items: center;
  gap: 10px;
  text-decoration: none;
}

.logo-icon {
  font-size: 1.5rem;
  line-height: 1;
}

.logo-text {
  font-family: var(--font-heading);
  font-size: 1.15rem;
  font-weight: 800;
  letter-spacing: -0.02em;
}

.logo-main  { color: white; }
.logo-accent { color: var(--accent); }

/* Links */
.navbar-links {
  display: flex;
  align-items: center;
  gap: 6px;
}

.nav-link {
  position: relative;
  color: rgba(255,255,255,0.75);
  font-size: 0.9rem;
  font-weight: 500;
  padding: 8px 12px;
  border-radius: var(--radius-md);
  text-decoration: none;
  transition: var(--transition);
  display: flex;
  align-items: center;
  gap: 6px;
}

.nav-link:hover {
  color: white;
  background: rgba(255,255,255,0.08);
}

.nav-link.router-link-active {
  color: var(--accent);
  background: rgba(0,195,255,0.1);
}

.nav-link--cta {
  background: var(--primary) !important;
  color: white !important;
  padding: 8px 16px;
  border-radius: var(--radius-pill);
  font-weight: 600;
}

.nav-link--cta:hover {
  background: var(--primary-dark) !important;
  transform: translateY(-1px);
}

.nav-link--admin {
  background: rgba(245,158,11,0.15) !important;
  color: var(--gold) !important;
}

.nav-badge {
  background: var(--primary);
  color: white;
  font-size: 0.68rem;
  font-weight: 700;
  padding: 2px 6px;
  border-radius: var(--radius-pill);
  min-width: 18px;
  text-align: center;
}

.nav-btn {
  background: transparent;
  border: 1.5px solid rgba(255,255,255,0.3);
  color: white;
  padding: 8px 18px;
  border-radius: var(--radius-pill);
  font-size: 0.9rem;
  font-weight: 600;
  text-decoration: none;
  transition: var(--transition);
}

.nav-btn:hover {
  background: white;
  color: var(--navy-dark);
  border-color: white;
}

/* Hamburger */
.hamburger {
  display: none;
  flex-direction: column;
  gap: 5px;
  background: none;
  border: none;
  cursor: pointer;
  padding: 8px;
}

.hamburger span {
  display: block;
  width: 22px;
  height: 2px;
  background: white;
  border-radius: 2px;
  transition: var(--transition);
}

/* Mobile Menu */
.mobile-menu {
  border-top: 1px solid rgba(255,255,255,0.08);
  padding: 12px 24px 20px;
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.mobile-link {
  color: rgba(255,255,255,0.8);
  font-size: 1rem;
  font-weight: 500;
  padding: 12px 14px;
  border-radius: var(--radius-md);
  text-decoration: none;
  transition: var(--transition-fast);
}

.mobile-link:hover {
  color: white;
  background: rgba(255,255,255,0.08);
}

.mobile-link.router-link-active {
  color: var(--accent);
}

.mobile-link--cta {
  margin-top: 8px;
  background: var(--primary);
  color: white !important;
  text-align: center;
  border-radius: var(--radius-pill);
  font-weight: 600;
}

@media (max-width: 900px) {
  .navbar-links { display: none; }
  .hamburger { display: flex; }
}
</style>
