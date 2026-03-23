<template>
  <div>
    <div class="page-header">
      <div class="container page-header-content">
        <h1>📬 Contacto</h1>
        <p>Estamos aquí para ayudarte</p>
      </div>
    </div>

    <div class="container contacto-layout">

      <!-- Info Panel -->
      <div class="contacto-info">
        <div class="card-base info-card">
          <h2>¿En qué podemos ayudarte?</h2>
          <p>Nuestro equipo responde en menos de 24 horas. También puedes encontrarnos en:</p>

          <div class="contact-items">
            <a href="mailto:info@sincochenotemueves.com" class="contact-item">
              <div class="contact-item-icon">✉️</div>
              <div>
                <strong>Email</strong>
                <span>info@sincochenotemueves.com</span>
              </div>
            </a>
            <a href="tel:600000000" class="contact-item">
              <div class="contact-item-icon">📞</div>
              <div>
                <strong>Teléfono</strong>
                <span>600 000 000</span>
              </div>
            </a>
            <div class="contact-item">
              <div class="contact-item-icon">📍</div>
              <div>
                <strong>Ubicación</strong>
                <span>Salamanca, España</span>
              </div>
            </div>
            <div class="contact-item">
              <div class="contact-item-icon">⏰</div>
              <div>
                <strong>Horario</strong>
                <span>Lun-Vie 9:00–18:00</span>
              </div>
            </div>
          </div>
        </div>

        <div class="card-base faq-card">
          <h3>Preguntas frecuentes</h3>
          <div class="faq-list">
            <div class="faq-item" v-for="faq in faqs" :key="faq.q" @click="faq.open = !faq.open">
              <div class="faq-q">
                <span>{{ faq.q }}</span>
                <span>{{ faq.open ? '▲' : '▼' }}</span>
              </div>
              <div v-if="faq.open" class="faq-a">{{ faq.a }}</div>
            </div>
          </div>
        </div>
      </div>

      <!-- Formulario -->
      <div class="card-base contacto-form-card">
        <h3>Envíanos un mensaje</h3>
        <p style="color: var(--text-muted); margin-bottom: 28px; font-size: 0.92rem">
          Completa el formulario y te responderemos lo antes posible.
        </p>

        <div v-if="enviado" class="alert alert-success">
          🎉 ¡Mensaje enviado! Te responderemos en menos de 24h.
        </div>

        <form v-else @submit.prevent="enviar">
          <div class="form-row-2">
            <div class="form-group">
              <label class="form-label">Nombre</label>
              <input v-model="nombre" class="form-input" placeholder="Juan" required />
            </div>
            <div class="form-group">
              <label class="form-label">Apellidos</label>
              <input v-model="apellidos" class="form-input" placeholder="García" required />
            </div>
          </div>

          <div class="form-group">
            <label class="form-label">Correo electrónico</label>
            <input v-model="correo" type="email" class="form-input" placeholder="tu@email.com" required />
          </div>

          <div class="form-group">
            <label class="form-label">Asunto</label>
            <input v-model="asunto" class="form-input" placeholder="¿En qué podemos ayudarte?" required />
          </div>

          <div class="form-group">
            <label class="form-label">Mensaje</label>
            <textarea v-model="mensaje" class="form-input form-textarea" placeholder="Descríbenos tu consulta con detalle..." required style="min-height: 140px"></textarea>
          </div>

          <button type="submit" class="btn btn-primary btn-enviar" :disabled="cargando">
            {{ cargando ? 'Enviando...' : '📨 Enviar mensaje' }}
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import api from '../services/api'

const nombre = ref('')
const apellidos = ref('')
const correo = ref('')
const asunto = ref('')
const mensaje = ref('')
const cargando = ref(false)
const enviado = ref(false)

const faqs = reactive([
  { q: '¿Cómo publico un vehículo?', a: 'Regístrate, accede a "Vender" y completa el formulario con los datos de tu vehículo. Es gratis.', open: false },
  { q: '¿Cómo funciona el pago?', a: 'Los pagos se gestionan de forma segura a través de nuestra plataforma. El dinero se libera cuando confirmas la entrega.', open: false },
  { q: '¿Puedo ver los vehículos sin registrarme?', a: 'Sí, puedes explorar el catálogo sin cuenta. Solo necesitas registrarte para comprar, vender o contactar con vendedores.', open: false },
])

const enviar = async () => {
  cargando.value = true
  try {
    await api.post('/contacto', { nombre: nombre.value, apellidos: apellidos.value, correo: correo.value, asunto: asunto.value, mensaje: mensaje.value })
    enviado.value = true
  } catch {
    alert('Error al enviar. Inténtalo de nuevo.')
  } finally { cargando.value = false }
}
</script>

<style scoped>
.contacto-layout {
  display: grid;
  grid-template-columns: 1fr 1.2fr;
  gap: 32px;
  padding: 40px 16px 80px;
  align-items: start;
}

.contacto-info { display: flex; flex-direction: column; gap: 24px; }

.info-card { padding: 36px; }
.info-card h2 { font-size: 1.4rem; margin-bottom: 12px; }
.info-card > p { color: var(--text-muted); line-height: 1.65; margin-bottom: 28px; font-size: 0.93rem; }

.contact-items { display: flex; flex-direction: column; gap: 16px; }

.contact-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 14px 18px;
  background: var(--bg-alt);
  border-radius: var(--radius-md);
  border: 1px solid var(--border-light);
  text-decoration: none;
  transition: var(--transition-fast);
}

a.contact-item:hover { border-color: var(--primary); background: rgba(0,119,255,0.05); }

.contact-item-icon {
  width: 40px;
  height: 40px;
  border-radius: var(--radius-md);
  background: var(--bg-card);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.1rem;
  flex-shrink: 0;
  box-shadow: var(--shadow-xs);
}

.contact-item strong { display: block; font-size: 0.85rem; color: var(--text); margin-bottom: 2px; }
.contact-item span { font-size: 0.82rem; color: var(--text-muted); }

/* FAQ */
.faq-card { padding: 28px; }
.faq-card h3 { font-size: 1.05rem; margin-bottom: 20px; }

.faq-list { display: flex; flex-direction: column; gap: 8px; }

.faq-item {
  border: 1px solid var(--border-light);
  border-radius: var(--radius-md);
  overflow: hidden;
  cursor: pointer;
}

.faq-q {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 18px;
  font-size: 0.9rem;
  font-weight: 600;
  color: var(--text);
  background: var(--bg-card);
  transition: var(--transition-fast);
}

.faq-item:hover .faq-q { background: var(--bg-alt); }

.faq-a {
  padding: 12px 18px 16px;
  font-size: 0.86rem;
  color: var(--text-muted);
  line-height: 1.65;
  border-top: 1px solid var(--border-light);
  background: var(--bg-alt);
}

/* Form */
.contacto-form-card { padding: 36px; }
.contacto-form-card h3 { font-size: 1.2rem; margin-bottom: 6px; }

.form-row-2 { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
.btn-enviar { width: 100%; padding: 15px; font-size: 1rem; margin-top: 6px; }

@media (max-width: 900px) {
  .contacto-layout { grid-template-columns: 1fr; }
  .form-row-2 { grid-template-columns: 1fr; gap: 0; }
}
</style>
