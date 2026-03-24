<template>
  <footer class="footer">
    <div class="footer-top">
      <div class="container footer-grid">

        <!-- Brand -->
        <div class="footer-brand">
          <router-link to="/" class="footer-logo">
            <span class="logo-icon">🚗</span>
            <span><span style="color:white">SinCoche</span><span style="color:var(--accent)">NoTeMueves</span></span>
          </router-link>
          <p class="footer-tagline">
            El marketplace de vehículos más completo de España. Compra, vende y alquila con total seguridad.
          </p>
          <div class="footer-social">
            <a href="#" class="social-btn" aria-label="Instagram">📸</a>
            <a href="https://x.com" target="_blank" rel="noopener noreferrer" class="social-btn" aria-label="X">🐦</a>
            <a href="#" class="social-btn" aria-label="LinkedIn">💼</a>
          </div>
        </div>

        <!-- Links -->
        <div class="footer-col">
          <h4 class="footer-col-title">Plataforma</h4>
          <router-link to="/" class="footer-link">Inicio</router-link>
          <router-link to="/vehiculos" class="footer-link">Catálogo</router-link>
          <router-link to="/publicar" class="footer-link">Vender vehículo</router-link>
          <router-link to="/favoritos" class="footer-link">Mis favoritos</router-link>
        </div>

        <!-- Cuenta -->
        <div class="footer-col">
          <h4 class="footer-col-title">Mi cuenta</h4>
          <router-link to="/login" class="footer-link">Iniciar sesión</router-link>
          <router-link to="/registro" class="footer-link">Crear cuenta</router-link>
          <router-link to="/perfil" class="footer-link">Mi perfil</router-link>
          <router-link to="/chat" class="footer-link">Chat</router-link>
        </div>

        <!-- Contacto -->
        <div class="footer-col">
          <h4 class="footer-col-title">Contacto</h4>
          <a href="mailto:info@sincochenotemueves.com" class="footer-link footer-contact-item">
            <span>✉️</span> info@sincochenotemueves.com
          </a>
          <a href="tel:600000000" class="footer-link footer-contact-item">
            <span>📞</span> 600 000 000
          </a>
          <div class="footer-contact-item footer-link">
            <span>📍</span> Salamanca, España
          </div>
        </div>

      </div>
    </div>

    <div class="footer-bottom">
      <div class="container footer-bottom-inner">
        <p>© 2026 SinCocheNoTeMueves — Proyecto DAW</p>
        <div class="footer-bottom-links">
          <router-link to="/politica-privacidad">Política de privacidad</router-link>
          <router-link to="/terminos-servicio">Términos de uso</router-link>
          <router-link to="/politica-cookies">Cookies</router-link>
        </div>
      </div>
    </div>
  </footer>
</template>

<script setup></script>

<style scoped>
.footer {
  background: var(--navy-dark);
  color: rgba(255,255,255,0.7);
  font-size: 0.9rem;
  margin-top: auto;
}

.footer-top { padding: 64px 0 48px; }

.footer-grid {
  display: grid;
  grid-template-columns: 1.8fr 1fr 1fr 1.3fr;
  gap: 40px;
}

/* Brand */
.footer-logo {
  display: flex;
  align-items: center;
  gap: 10px;
  font-family: var(--font-heading);
  font-size: 1.1rem;
  font-weight: 800;
  text-decoration: none;
  margin-bottom: 16px;
}

.logo-icon { font-size: 1.4rem; }

.footer-tagline {
  line-height: 1.7;
  color: rgba(255,255,255,0.5);
  max-width: 280px;
  margin-bottom: 24px;
}

.footer-social {
  display: flex;
  gap: 10px;
}

.social-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 38px;
  height: 38px;
  border-radius: var(--radius-md);
  background: rgba(255,255,255,0.07);
  font-size: 1rem;
  text-decoration: none;
  transition: var(--transition);
  border: 1px solid rgba(255,255,255,0.1);
}

.social-btn:hover {
  background: var(--primary);
  border-color: var(--primary);
  transform: translateY(-2px);
}

/* Cols */
.footer-col-title {
  color: white;
  font-size: 0.82rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.1em;
  margin-bottom: 18px;
}

.footer-link {
  display: flex;
  align-items: center;
  gap: 8px;
  color: rgba(255,255,255,0.5);
  text-decoration: none;
  padding: 5px 0;
  transition: var(--transition-fast);
}

.footer-link:hover { color: var(--accent); }

.footer-contact-item { gap: 8px; }

/* Bottom */
.footer-bottom {
  border-top: 1px solid rgba(255,255,255,0.07);
  padding: 20px 0;
  background: rgba(0,0,0,0.2);
}

.footer-bottom-inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 12px;
}

.footer-bottom p {
  font-size: 0.82rem;
  color: rgba(255,255,255,0.35);
}

.footer-bottom-links {
  display: flex;
  gap: 20px;
}

.footer-bottom-links a {
  font-size: 0.82rem;
  color: rgba(255,255,255,0.35);
  text-decoration: none;
  transition: var(--transition-fast);
}

.footer-bottom-links a:hover { color: rgba(255,255,255,0.7); }

@media (max-width: 900px) {
  .footer-grid { grid-template-columns: 1fr 1fr; }
}

@media (max-width: 560px) {
  .footer-grid { grid-template-columns: 1fr; }
  .footer-bottom-inner { flex-direction: column; text-align: center; }
}
</style>
