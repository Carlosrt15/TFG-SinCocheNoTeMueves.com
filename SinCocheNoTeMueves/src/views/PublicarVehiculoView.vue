<template>
  <div>
    <div class="page-header">
      <div class="container page-header-content">
        <h1>🚗 Publicar vehículo</h1>
        <p>Llega a miles de compradores en minutos</p>
      </div>
    </div>

    <div class="container publicar-layout">

      <!-- Formulario -->
      <div class="publicar-form-wrap card-base">
        <div class="form-section-title">📋 Datos del vehículo</div>

        <div v-if="mensaje" class="alert" :class="error ? 'alert-error' : 'alert-success'">
          {{ error ? '⚠️' : '✅' }} {{ mensaje }}
        </div>

        <form @submit.prevent="publicar">
          <div class="form-row-2">
            <div class="form-group">
              <label class="form-label">Marca *</label>
              <input v-model="form.marca" class="form-input" placeholder="BMW, Audi, Toyota..." required />
            </div>
            <div class="form-group">
              <label class="form-label">Modelo *</label>
              <input v-model="form.modelo" class="form-input" placeholder="Serie 3, A4, Corolla..." required />
            </div>
          </div>

          <div class="form-row-3">
            <div class="form-group">
              <label class="form-label">Estado *</label>
              <select v-model="form.estado" class="form-input form-select" required>
                <option value="">Seleccionar...</option>
                <option value="Nuevo">Nuevo</option>
                <option value="Usado">Usado</option>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label">Precio (€) *</label>
              <input v-model.number="form.precio" type="number" class="form-input" placeholder="25000" required min="0" />
            </div>
            <div class="form-group">
              <label class="form-label">Año</label>
              <input v-model.number="form.anio" type="number" class="form-input" placeholder="2021" min="1990" :max="new Date().getFullYear()" />
            </div>
          </div>

          <div class="form-row-2">
            <div class="form-group">
              <label class="form-label">Kilómetros</label>
              <input v-model.number="form.km" type="number" class="form-input" placeholder="45000" min="0" />
            </div>
            <div class="form-group">
              <label class="form-label">Combustible</label>
              <select v-model="form.combustible" class="form-input form-select">
                <option value="">Seleccionar...</option>
                <option>Gasolina</option>
                <option>Diésel</option>
                <option>Eléctrico</option>
                <option>Híbrido</option>
                <option>GLP</option>
              </select>
            </div>
          </div>

          <div class="form-group">
            <label class="form-label">URL de imagen</label>
            <input v-model="form.imagen" type="url" class="form-input" placeholder="https://..." />
          </div>

          <div class="form-group">
            <label class="form-label">Descripción</label>
            <textarea v-model="form.descripcion" class="form-input form-textarea" placeholder="Describe el estado, extras, historial de mantenimiento..."></textarea>
          </div>

          <button type="submit" class="btn btn-primary btn-publicar" :disabled="cargando">
            {{ cargando ? 'Publicando...' : '🚀 Publicar anuncio' }}
          </button>
        </form>
      </div>

      <!-- Tips -->
      <div class="publicar-tips">
        <div class="tip-card card-base">
          <h3>💡 Consejos para vender</h3>
          <div class="tip-list">
            <div class="tip-item">
              <span class="tip-icon">📸</span>
              <div>
                <strong>Buenas fotos</strong>
                <p>Los anuncios con fotos de calidad reciben un 80% más de visitas</p>
              </div>
            </div>
            <div class="tip-item">
              <span class="tip-icon">💰</span>
              <div>
                <strong>Precio competitivo</strong>
                <p>Consulta el valor de mercado para atraer más compradores</p>
              </div>
            </div>
            <div class="tip-item">
              <span class="tip-icon">📝</span>
              <div>
                <strong>Descripción completa</strong>
                <p>Incluye extras, historial y estado para generar confianza</p>
              </div>
            </div>
          </div>
        </div>

        <div class="trust-card card-base">
          <h4>🔒 Tu anuncio está protegido</h4>
          <p>Revisamos cada publicación para garantizar la calidad de nuestra plataforma.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { crearVehiculo } from '../services/vehiculoService'

const form = reactive({ marca: '', modelo: '', estado: '', precio: null, anio: null, km: null, combustible: '', imagen: '', descripcion: '' })
const mensaje = ref('')
const error = ref(false)
const cargando = ref(false)

const publicar = async () => {
  cargando.value = true
  error.value = false
  try {
    await crearVehiculo(form)
    mensaje.value = '¡Vehículo publicado correctamente! Ya está visible en el catálogo.'
    Object.keys(form).forEach(k => form[k] = k === 'precio' || k === 'anio' || k === 'km' ? null : '')
  } catch {
    error.value = true
    mensaje.value = 'Error al publicar el vehículo. Inténtalo de nuevo.'
  } finally { cargando.value = false }
}
</script>

<style scoped>
.publicar-layout {
  display: grid;
  grid-template-columns: 1fr 340px;
  gap: 32px;
  padding: 40px 16px 80px;
  align-items: start;
}

.publicar-form-wrap {
  padding: 36px;
}

.form-section-title {
  font-family: var(--font-heading);
  font-size: 1.1rem;
  font-weight: 700;
  margin-bottom: 28px;
  padding-bottom: 16px;
  border-bottom: 1px solid var(--border-light);
}

.form-row-2 { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
.form-row-3 { display: grid; grid-template-columns: 1fr 1fr 1fr; gap: 16px; }

.btn-publicar { width: 100%; padding: 16px; font-size: 1rem; margin-top: 8px; }

/* Tips */
.publicar-tips {
  display: flex;
  flex-direction: column;
  gap: 20px;
  position: sticky;
  top: calc(var(--navbar-h) + 20px);
}

.tip-card { padding: 28px; }
.tip-card h3 { font-size: 1rem; margin-bottom: 20px; }

.tip-list { display: flex; flex-direction: column; gap: 16px; }

.tip-item { display: flex; gap: 12px; align-items: flex-start; }
.tip-icon { font-size: 1.2rem; flex-shrink: 0; margin-top: 1px; }
.tip-item strong { display: block; font-size: 0.9rem; margin-bottom: 3px; }
.tip-item p { font-size: 0.8rem; color: var(--text-muted); margin: 0; line-height: 1.5; }

.trust-card {
  padding: 20px 24px;
  background: linear-gradient(135deg, rgba(0,119,255,0.06), rgba(0,195,255,0.06));
  border-color: rgba(0,119,255,0.15);
}

.trust-card h4 { font-size: 0.95rem; margin-bottom: 8px; }
.trust-card p { font-size: 0.83rem; color: var(--text-muted); line-height: 1.6; margin: 0; }

@media (max-width: 900px) {
  .publicar-layout { grid-template-columns: 1fr; }
  .publicar-tips { position: static; }
  .form-row-2, .form-row-3 { grid-template-columns: 1fr; gap: 0; }
}
</style>
