<template>
  <div class="home">

    <!-- HERO -->
    <section class="hero">
      <div class="hero-bg"></div>
      <div class="hero-overlay"></div>
      <div class="container hero-content">
        <div class="hero-badge animate-up">🏆 El marketplace nº1 de vehículos en España</div>
        <h1 class="hero-title animate-up animate-up-1">
          Tu próximo coche<br/>
          <span class="hero-title--accent">te está esperando</span>
        </h1>
        <p class="hero-subtitle animate-up animate-up-2">
          Compra, vende o alquila vehículos de forma rápida y 100% segura.<br/>
          No te quedes con un SsangYong cuando puedes conducir tus sueños.
        </p>
        <div class="hero-actions animate-up animate-up-3">
          <router-link to="/vehiculos" class="btn btn-primary btn-hero">
            🔍 Ver vehículos
          </router-link>
          <router-link to="/publicar" class="btn btn-ghost btn-hero">
            + Vender mi coche
          </router-link>
        </div>
      </div>
      <div class="hero-scroll-indicator">
        <span>Descubre más</span>
        <span class="scroll-arrow">↓</span>
      </div>
    </section>

    <!-- STATS -->
    <section class="stats-bar">
      <div class="container stats-bar-inner">
        <div class="stat-item">
          <span class="stat-num">+2.400</span>
          <span class="stat-label">Vehículos publicados</span>
        </div>
        <div class="stat-divider"></div>
        <div class="stat-item">
          <span class="stat-num">+18.000</span>
          <span class="stat-label">Usuarios registrados</span>
        </div>
        <div class="stat-divider"></div>
        <div class="stat-item">
          <span class="stat-num">+5.600</span>
          <span class="stat-label">Ventas completadas</span>
        </div>
        <div class="stat-divider"></div>
        <div class="stat-item">
          <span class="stat-num">4.9 ⭐</span>
          <span class="stat-label">Valoración media</span>
        </div>
      </div>
    </section>

    <!-- SERVICIOS -->
    <section class="section services">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">¿Qué puedes hacer?</h2>
          <p class="section-subtitle">Todo lo que necesitas en una sola plataforma</p>
        </div>
        <div class="services-grid">
          <div class="service-card animate-up">
            <div class="service-icon service-icon--blue">🛒</div>
            <h3>Compra</h3>
            <p>Encuentra el vehículo perfecto entre miles de anuncios verificados. Filtra por marca, precio, año y más.</p>
            <router-link to="/vehiculos" class="service-link">Explorar catálogo →</router-link>
          </div>
          <div class="service-card animate-up animate-up-1">
            <div class="service-icon service-icon--green">💰</div>
            <h3>Vende</h3>
            <p>Publica tu vehículo en minutos y llega a miles de compradores potenciales cada día.</p>
            <router-link to="/publicar" class="service-link">Publicar anuncio →</router-link>
          </div>
          <div class="service-card animate-up animate-up-2">
            <div class="service-icon service-icon--gold">🔑</div>
            <h3>Alquila</h3>
            <p>Alquila un coche por días o semanas con total flexibilidad y sin compromisos a largo plazo.</p>
            <router-link to="/vehiculos" class="service-link">Ver disponibles →</router-link>
          </div>
        </div>
      </div>
    </section>

    <!-- POR QUÉ ELEGIRNOS -->
    <section class="section why-us">
      <div class="container why-us-inner">
        <div class="why-us-text animate-up">
          <span class="why-us-pretitle">¿Por qué nosotros?</span>
          <h2 class="section-title">La plataforma más<br/>segura y completa</h2>
          <p style="color: var(--text-muted); margin: 20px 0 32px; line-height: 1.75;">
            Llevamos años conectando compradores y vendedores con total transparencia. Cada anuncio es revisado y los pagos están protegidos.
          </p>
          <div class="why-features">
            <div class="why-feature">
              <span class="why-feature-icon">✅</span>
              <div>
                <strong>Anuncios verificados</strong>
                <p>Revisamos cada publicación para garantizar la autenticidad.</p>
              </div>
            </div>
            <div class="why-feature">
              <span class="why-feature-icon">🔒</span>
              <div>
                <strong>Transacciones seguras</strong>
                <p>Tu dinero está protegido hasta que confirmes la compra.</p>
              </div>
            </div>
            <div class="why-feature">
              <span class="why-feature-icon">💬</span>
              <div>
                <strong>Chat directo</strong>
                <p>Habla directamente con el vendedor sin intermediarios.</p>
              </div>
            </div>
          </div>
        </div>
        <div class="why-us-image animate-up animate-up-2">
          <img src="https://images.unsplash.com/photo-1469854523086-cc02fe5d8800?w=700&q=80" alt="Concesionario premium" />
          <div class="why-us-badge-float">
            <span>🏅</span>
            <div>
              <strong>Garantía</strong>
              <small>en todas las compras</small>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- CTA FINAL -->
    <section class="cta-section">
      <div class="container cta-inner">
        <h2>¿Listo para encontrar tu coche ideal?</h2>
        <p>Únete a miles de usuarios que ya confían en nosotros</p>
        <div class="cta-actions">
          <router-link to="/registro" class="btn btn-primary btn-hero">
            Crear cuenta gratis
          </router-link>
          <router-link to="/vehiculos" class="btn btn-ghost btn-hero">
            Ver catálogo
          </router-link>
        </div>
      </div>
    </section>

  </div>
</template>

<script setup></script>

<style scoped>
/* Hero */
.hero {
  position: relative;
  min-height: 88vh;
  display: flex;
  align-items: center;
  overflow: hidden;
}

.hero-bg {
  position: absolute;
  inset: 0;
  background-image: url('https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=1600&q=80');
  background-size: cover;
  background-position: center 30%;
  transform: scale(1.05);
  animation: heroScale 12s ease-in-out infinite alternate;
}

@keyframes heroScale {
  from { transform: scale(1.05); }
  to   { transform: scale(1.08); }
}

.hero-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(
    135deg,
    rgba(10,20,40,0.88) 0%,
    rgba(15,30,60,0.75) 50%,
    rgba(0,0,0,0.6) 100%
  );
}

.hero-content {
  position: relative;
  z-index: 2;
  color: white;
  padding: 80px 16px;
}

.hero-badge {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  background: rgba(0,119,255,0.25);
  border: 1px solid rgba(0,195,255,0.35);
  color: var(--accent);
  font-size: 0.82rem;
  font-weight: 600;
  padding: 8px 16px;
  border-radius: var(--radius-pill);
  margin-bottom: 28px;
  backdrop-filter: blur(8px);
  letter-spacing: 0.04em;
}

.hero-title {
  font-size: clamp(2.4rem, 6vw, 4.2rem);
  font-weight: 800;
  line-height: 1.1;
  color: white;
  margin-bottom: 24px;
}

.hero-title--accent {
  background: linear-gradient(135deg, var(--primary-light), var(--accent));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.hero-subtitle {
  font-size: clamp(1rem, 2vw, 1.2rem);
  color: rgba(255,255,255,0.75);
  max-width: 580px;
  margin-bottom: 40px;
  line-height: 1.75;
}

.hero-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 14px;
}

.btn-hero {
  padding: 16px 34px;
  font-size: 1rem;
}

.hero-scroll-indicator {
  position: absolute;
  bottom: 32px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 2;
  text-align: center;
  color: rgba(255,255,255,0.5);
  font-size: 0.8rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 6px;
}

.scroll-arrow {
  animation: bounce 1.8s infinite;
  font-size: 1.2rem;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50%       { transform: translateY(8px); }
}

/* Stats Bar */
.stats-bar {
  background: var(--navy-dark);
  padding: 32px 0;
  border-bottom: 1px solid rgba(255,255,255,0.06);
}

.stats-bar-inner {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0;
  flex-wrap: wrap;
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 12px 40px;
  gap: 4px;
}

.stat-num {
  font-family: var(--font-heading);
  font-size: 1.8rem;
  font-weight: 800;
  color: var(--accent);
}

.stat-label {
  font-size: 0.82rem;
  color: rgba(255,255,255,0.5);
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.stat-divider {
  width: 1px;
  height: 40px;
  background: rgba(255,255,255,0.1);
}

/* Services */
.section-header {
  text-align: center;
  margin-bottom: 52px;
}

.services-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 28px;
}

.service-card {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 36px 30px;
  border: 1px solid var(--border-light);
  box-shadow: var(--shadow-sm);
  transition: var(--transition-slow);
  position: relative;
  overflow: hidden;
}

.service-card::before {
  content: '';
  position: absolute;
  top: 0; left: 0; right: 0;
  height: 3px;
  background: linear-gradient(90deg, var(--primary), var(--accent));
  opacity: 0;
  transition: var(--transition);
}

.service-card:hover { transform: translateY(-8px); box-shadow: var(--shadow-lg); }
.service-card:hover::before { opacity: 1; }

.service-icon {
  width: 60px;
  height: 60px;
  border-radius: var(--radius-lg);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.7rem;
  margin-bottom: 20px;
}

.service-icon--blue  { background: rgba(0,119,255,0.1); }
.service-icon--green { background: rgba(16,185,129,0.1); }
.service-icon--gold  { background: rgba(245,158,11,0.1); }

.service-card h3 {
  font-size: 1.3rem;
  margin-bottom: 12px;
}

.service-card p {
  color: var(--text-muted);
  line-height: 1.7;
  font-size: 0.95rem;
  margin-bottom: 20px;
}

.service-link {
  color: var(--primary);
  font-weight: 600;
  font-size: 0.9rem;
  transition: var(--transition-fast);
}

.service-link:hover { color: var(--primary-dark); }

/* Why Us */
.why-us { background: var(--bg-alt); }

.why-us-inner {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 72px;
  align-items: center;
}

.why-us-pretitle {
  font-size: 0.8rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.12em;
  color: var(--primary);
  margin-bottom: 12px;
  display: block;
}

.why-features { display: flex; flex-direction: column; gap: 20px; }

.why-feature {
  display: flex;
  gap: 16px;
  align-items: flex-start;
}

.why-feature-icon {
  font-size: 1.3rem;
  flex-shrink: 0;
  margin-top: 2px;
}

.why-feature strong {
  display: block;
  font-size: 0.97rem;
  margin-bottom: 4px;
  color: var(--text);
}

.why-feature p {
  font-size: 0.87rem;
  color: var(--text-muted);
  line-height: 1.6;
  margin: 0;
}

.why-us-image {
  position: relative;
}

.why-us-image img {
  width: 100%;
  height: 420px;
  object-fit: cover;
  border-radius: var(--radius-xl);
  box-shadow: var(--shadow-lg);
}

.why-us-badge-float {
  position: absolute;
  bottom: -20px;
  left: -20px;
  background: white;
  border-radius: var(--radius-lg);
  padding: 16px 20px;
  display: flex;
  align-items: center;
  gap: 12px;
  box-shadow: var(--shadow-md);
  font-size: 1.5rem;
}

.why-us-badge-float div { display: flex; flex-direction: column; gap: 2px; }
.why-us-badge-float strong { font-size: 0.95rem; color: var(--text); }
.why-us-badge-float small { font-size: 0.8rem; color: var(--text-muted); }

/* CTA */
.cta-section {
  background: linear-gradient(135deg, var(--navy-dark), var(--navy));
  padding: 90px 0;
  position: relative;
  overflow: hidden;
}

.cta-section::before {
  content: '';
  position: absolute;
  top: -100px; right: -100px;
  width: 400px; height: 400px;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(0,119,255,0.2) 0%, transparent 70%);
}

.cta-inner {
  position: relative;
  z-index: 1;
  text-align: center;
}

.cta-inner h2 {
  font-size: clamp(1.8rem, 4vw, 2.8rem);
  color: white;
  margin-bottom: 14px;
}

.cta-inner p {
  color: rgba(255,255,255,0.6);
  font-size: 1.05rem;
  margin-bottom: 36px;
}

.cta-actions {
  display: flex;
  justify-content: center;
  gap: 14px;
  flex-wrap: wrap;
}

/* Responsive */
@media (max-width: 900px) {
  .services-grid { grid-template-columns: 1fr; }
  .why-us-inner { grid-template-columns: 1fr; }
  .why-us-badge-float { display: none; }
  .stat-item { padding: 12px 20px; }
  .stat-divider { display: none; }
}
</style>
