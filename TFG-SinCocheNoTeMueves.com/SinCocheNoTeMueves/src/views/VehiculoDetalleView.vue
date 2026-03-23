<template>
  <div class="detalle-page">
    <div class="container detalle-container">

      <!-- Breadcrumb -->
      <nav class="breadcrumb">
        <router-link to="/">Inicio</router-link>
        <span>›</span>
        <router-link to="/vehiculos">Vehículos</router-link>
        <span>›</span>
        <span>{{ vehiculo.marca }} {{ vehiculo.modelo }}</span>
      </nav>

      <div class="detalle-grid">

        <!-- Galería -->
        <div class="detalle-gallery">
          <div class="gallery-main">
            <img :src="vehiculo.imagen || 'https://images.unsplash.com/photo-1544636331-e26879cd4d9b?w=900&q=80'"
                 :alt="`${vehiculo.marca} ${vehiculo.modelo}`" />
            <span v-if="!vehiculo.disponible" class="gallery-vendido-badge">VENDIDO</span>
          </div>
        </div>

        <!-- Info & Compra -->
        <div class="detalle-info">

          <div class="detalle-header">
            <span class="detalle-estado-chip" :class="vehiculo.disponible ? 'chip-disponible' : 'chip-vendido'">
              {{ vehiculo.disponible ? '✅ Disponible' : '🔴 Vendido' }}
            </span>
            <h1 class="detalle-titulo">{{ vehiculo.marca }} {{ vehiculo.modelo }}</h1>
            <p class="detalle-precio">{{ vehiculo.precio?.toLocaleString('es-ES') }} €</p>
          </div>

          <!-- Specs -->
          <div class="detalle-specs">
            <div class="spec-item">
              <span class="spec-icon">📋</span>
              <div>
                <span class="spec-label">Estado</span>
                <span class="spec-value">{{ vehiculo.estado || '—' }}</span>
              </div>
            </div>
            <div class="spec-item" v-if="vehiculo.anio">
              <span class="spec-icon">📅</span>
              <div>
                <span class="spec-label">Año</span>
                <span class="spec-value">{{ vehiculo.anio }}</span>
              </div>
            </div>
            <div class="spec-item" v-if="vehiculo.km">
              <span class="spec-icon">🛣️</span>
              <div>
                <span class="spec-label">Kilómetros</span>
                <span class="spec-value">{{ vehiculo.km?.toLocaleString() }} km</span>
              </div>
            </div>
            <div class="spec-item">
              <span class="spec-icon">⛽</span>
              <div>
                <span class="spec-label">Combustible</span>
                <span class="spec-value">{{ vehiculo.combustible || 'Gasolina' }}</span>
              </div>
            </div>
          </div>

          <!-- Compra -->
          <div class="detalle-compra">
            <div v-if="vehiculo.disponible">
              <button class="btn btn-primary btn-compra" @click="confirmarCompra">
                🛒 Comprar ahora — {{ vehiculo.precio?.toLocaleString('es-ES') }} €
              </button>
              <div class="compra-garantias">
                <span>🔒 Pago seguro</span>
                <span>✅ Garantía incluida</span>
                <span>📞 Soporte 24h</span>
              </div>
            </div>
            <div v-else class="vendido-notice">
              <span class="vendido-icon">🔴</span>
              <div>
                <strong>Este vehículo ya no está disponible</strong>
                <p>Explora otros vehículos similares en nuestro catálogo</p>
              </div>
            </div>

            <div v-if="mensaje" class="alert alert-success" style="margin-top:16px">
              🎉 {{ mensaje }}
            </div>
          </div>

          <!-- Vendedor -->
          <div class="detalle-vendedor">
            <div class="vendedor-avatar">👤</div>
            <div>
              <strong>Vendedor verificado</strong>
              <p>Miembro desde 2024 · <span style="color:var(--success)">✓ Verificado</span></p>
            </div>
            <router-link to="/chat" class="btn btn-outline btn-sm">💬 Contactar</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getVehiculo } from '../services/vehiculoService'

const route = useRoute()
const mensaje = ref('')
const cargando = ref(true)

const vehiculo = reactive({ id: null, marca: '', modelo: '', estado: '', precio: 0, disponible: true })

onMounted(async () => {
  try {
    const data = await getVehiculo(route.params.id)
    Object.assign(vehiculo, data)
  } catch { mensaje.value = 'Error al cargar el vehículo' }
  finally { cargando.value = false }
})

const confirmarCompra = () => {
  if (window.confirm(`¿Confirmas la compra del ${vehiculo.marca} ${vehiculo.modelo} por ${vehiculo.precio?.toLocaleString('es-ES')} €?`)) {
    let compras = JSON.parse(localStorage.getItem('compras')) || []
    compras.push({ id: vehiculo.id, marca: vehiculo.marca, modelo: vehiculo.modelo, precio: vehiculo.precio, fecha: new Date().toLocaleDateString('es-ES') })
    localStorage.setItem('compras', JSON.stringify(compras))
    vehiculo.disponible = false
    mensaje.value = '¡Compra realizada con éxito! Recibirás un email de confirmación.'
  }
}
</script>

<style scoped>
.detalle-page {
  background: var(--bg);
  min-height: calc(100vh - var(--navbar-h));
  padding: 40px 0 80px;
}

.breadcrumb {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 0.83rem;
  color: var(--text-muted);
  margin-bottom: 32px;
}

.breadcrumb a { color: var(--primary); text-decoration: none; }
.breadcrumb a:hover { text-decoration: underline; }

.detalle-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 48px;
  align-items: start;
}

/* Gallery */
.gallery-main {
  position: relative;
  border-radius: var(--radius-xl);
  overflow: hidden;
  box-shadow: var(--shadow-lg);
  background: var(--bg-alt);
}

.gallery-main img {
  width: 100%;
  height: 420px;
  object-fit: cover;
}

.gallery-vendido-badge {
  position: absolute;
  inset: 0;
  background: rgba(0,0,0,0.55);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-family: var(--font-heading);
  font-size: 2.5rem;
  font-weight: 800;
  letter-spacing: 0.1em;
}

/* Info */
.detalle-header { margin-bottom: 28px; }

.detalle-estado-chip {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  font-size: 0.8rem;
  font-weight: 700;
  padding: 5px 14px;
  border-radius: var(--radius-pill);
  margin-bottom: 12px;
}

.chip-disponible { background: var(--success-bg); color: #065f46; }
.chip-vendido    { background: var(--danger-bg); color: #991b1b; }

.detalle-titulo {
  font-size: clamp(1.6rem, 3vw, 2.2rem);
  margin-bottom: 12px;
}

.detalle-precio {
  font-family: var(--font-heading);
  font-size: 2.2rem;
  font-weight: 800;
  color: var(--primary);
}

/* Specs */
.detalle-specs {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 14px;
  margin-bottom: 32px;
  padding: 24px;
  background: var(--bg-alt);
  border-radius: var(--radius-lg);
  border: 1px solid var(--border-light);
}

.spec-item {
  display: flex;
  align-items: center;
  gap: 12px;
}

.spec-icon { font-size: 1.2rem; }

.spec-label {
  display: block;
  font-size: 0.73rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  color: var(--text-muted);
}

.spec-value {
  display: block;
  font-size: 0.95rem;
  font-weight: 600;
  color: var(--text);
}

/* Compra */
.detalle-compra { margin-bottom: 28px; }

.btn-compra {
  width: 100%;
  padding: 18px;
  font-size: 1.05rem;
  border-radius: var(--radius-lg);
  margin-bottom: 14px;
}

.compra-garantias {
  display: flex;
  gap: 18px;
  font-size: 0.8rem;
  color: var(--text-muted);
  justify-content: center;
}

.vendido-notice {
  background: var(--danger-bg);
  border-radius: var(--radius-lg);
  padding: 20px 24px;
  display: flex;
  gap: 16px;
  align-items: center;
}

.vendido-icon { font-size: 1.5rem; }
.vendido-notice strong { display: block; color: #991b1b; margin-bottom: 4px; }
.vendido-notice p { font-size: 0.88rem; color: var(--text-muted); margin: 0; }

/* Vendedor */
.detalle-vendedor {
  background: var(--bg-card);
  border: 1px solid var(--border-light);
  border-radius: var(--radius-lg);
  padding: 20px 24px;
  display: flex;
  align-items: center;
  gap: 16px;
}

.vendedor-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  background: var(--bg-alt);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.4rem;
  flex-shrink: 0;
}

.detalle-vendedor div:not(.vendedor-avatar) { flex: 1; }
.detalle-vendedor strong { display: block; font-size: 0.95rem; margin-bottom: 3px; }
.detalle-vendedor p { font-size: 0.82rem; color: var(--text-muted); margin: 0; }

@media (max-width: 900px) {
  .detalle-grid { grid-template-columns: 1fr; }
  .gallery-main img { height: 280px; }
  .detalle-specs { grid-template-columns: 1fr 1fr; }
}

@media (max-width: 480px) {
  .detalle-specs { grid-template-columns: 1fr; }
  .compra-garantias { flex-direction: column; align-items: center; gap: 8px; }
}
</style>
