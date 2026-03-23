<template>
  <div class="chat-page">
    <div class="page-header">
      <div class="container page-header-content">
        <h1>💬 Chat</h1>
        <p>Habla directamente con vendedores y compradores</p>
      </div>
    </div>

    <div class="container chat-layout">

      <!-- Sidebar conversaciones -->
      <div class="chat-sidebar card-base">
        <div class="chat-search-wrap">
          <input class="form-input" placeholder="🔍 Buscar conversación..." v-model="busqueda" />
        </div>
        <div class="conversations-list">
          <div
            v-for="conv in conversacionesFiltradas"
            :key="conv.id"
            class="conversation-item"
            :class="{ 'conversation-item--active': convActiva === conv.id }"
            @click="convActiva = conv.id"
          >
            <div class="conv-avatar">{{ conv.avatar }}</div>
            <div class="conv-info">
              <div class="conv-header">
                <strong class="conv-nombre">{{ conv.nombre }}</strong>
                <span class="conv-tiempo">{{ conv.tiempo }}</span>
              </div>
              <p class="conv-preview">{{ conv.ultimo }}</p>
              <p class="conv-vehiculo">🚗 {{ conv.vehiculo }}</p>
            </div>
            <span v-if="conv.noLeidos > 0" class="conv-badge">{{ conv.noLeidos }}</span>
          </div>

          <div v-if="conversacionesFiltradas.length === 0" class="empty-conversations">
            <p>Sin conversaciones</p>
          </div>
        </div>
      </div>

      <!-- Área de chat -->
      <div class="chat-main card-base">

        <!-- Sin conversación activa -->
        <div v-if="!convActiva" class="chat-placeholder">
          <div class="placeholder-icon">💬</div>
          <h3>Selecciona una conversación</h3>
          <p>Elige una conversación de la lista o inicia una nueva desde el detalle de un vehículo</p>
          <router-link to="/vehiculos" class="btn btn-primary">Ver vehículos</router-link>
        </div>

        <template v-else>
          <!-- Header del chat -->
          <div class="chat-header">
            <div class="chat-header-avatar">{{ convActual?.avatar }}</div>
            <div>
              <strong>{{ convActual?.nombre }}</strong>
              <p>{{ convActual?.vehiculo }}</p>
            </div>
            <div class="chat-header-actions">
              
              <button class="chat-action-btn" title="Más opciones">⋯</button>
            </div>
          </div>

          <!-- Mensajes -->
          <div class="chat-messages" ref="messagesEl">
            <div
              v-for="msg in mensajesActuales"
              :key="msg.id"
              class="msg-wrap"
              :class="msg.propio ? 'msg-wrap--propio' : 'msg-wrap--otro'"
            >
              <div class="msg-bubble" :class="msg.propio ? 'msg-bubble--propio' : 'msg-bubble--otro'">
                {{ msg.texto }}
              </div>
              <span class="msg-hora">{{ msg.hora }}</span>
            </div>
          </div>

          <!-- Input mensaje -->
          <div class="chat-input-area">
            <input
              v-model="nuevoMensaje"
              class="chat-input"
              placeholder="Escribe un mensaje..."
              @keyup.enter="enviarMensaje"
            />
            <button class="chat-send-btn" @click="enviarMensaje" :disabled="!nuevoMensaje.trim()">
              ➤
            </button>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, reactive, nextTick } from 'vue'

const busqueda = ref('')
const convActiva = ref(null)
const nuevoMensaje = ref('')
const messagesEl = ref(null)

const conversaciones = reactive([
  { id: 1, nombre: 'Carlos Ruiz', avatar: 'CR', vehiculo: 'BMW Serie 3 2021', ultimo: '¿Está disponible el coche?', tiempo: '10:30', noLeidos: 2,
    mensajes: [
      { id: 1, texto: '¡Hola! Vi tu anuncio del BMW, ¿sigue disponible?', propio: false, hora: '10:25' },
      { id: 2, texto: '¡Hola Carlos! Sí, sigue disponible.', propio: true, hora: '10:28' },
      { id: 3, texto: '¿Está disponible el coche?', propio: false, hora: '10:30' },
    ]
  },
  { id: 2, nombre: 'Ana Martínez', avatar: 'AM', vehiculo: 'Audi A4 2020', ultimo: 'Perfecto, me interesa', tiempo: 'Ayer', noLeidos: 0,
    mensajes: [
      { id: 1, texto: '¿Cuántos km tiene el Audi?', propio: false, hora: '09:00' },
      { id: 2, texto: 'Tiene 45.000 km, revisión reciente.', propio: true, hora: '09:05' },
      { id: 3, texto: 'Perfecto, me interesa', propio: false, hora: '09:10' },
    ]
  },
])

const conversacionesFiltradas = computed(() => {
  if (!busqueda.value) return conversaciones
  return conversaciones.filter(c =>
    c.nombre.toLowerCase().includes(busqueda.value.toLowerCase()) ||
    c.vehiculo.toLowerCase().includes(busqueda.value.toLowerCase())
  )
})

const convActual = computed(() => conversaciones.find(c => c.id === convActiva.value))
const mensajesActuales = computed(() => convActual.value?.mensajes || [])

const enviarMensaje = async () => {
  const texto = nuevoMensaje.value.trim()
  if (!texto || !convActiva.value) return
  const conv = conversaciones.find(c => c.id === convActiva.value)
  conv.mensajes.push({ id: Date.now(), texto, propio: true, hora: new Date().toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit' }) })
  conv.ultimo = texto
  nuevoMensaje.value = ''
  await nextTick()
  if (messagesEl.value) messagesEl.value.scrollTop = messagesEl.value.scrollHeight
}
</script>

<style scoped>
.chat-page { background: var(--bg); }

.chat-layout {
  display: grid;
  grid-template-columns: 340px 1fr;
  gap: 24px;
  padding: 40px 16px 60px;
  height: calc(100vh - var(--navbar-h) - 200px);
  min-height: 500px;
  align-items: start;
}

/* Sidebar */
.chat-sidebar {
  display: flex;
  flex-direction: column;
  height: 600px;
  overflow: hidden;
}

.chat-search-wrap {
  padding: 16px;
  border-bottom: 1px solid var(--border-light);
}

.conversations-list {
  flex: 1;
  overflow-y: auto;
}

.conversation-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  padding: 16px 18px;
  border-bottom: 1px solid var(--border-light);
  cursor: pointer;
  transition: var(--transition-fast);
  position: relative;
}

.conversation-item:hover { background: var(--bg-alt); }
.conversation-item--active { background: rgba(0,119,255,0.06); border-left: 3px solid var(--primary); }

.conv-avatar {
  width: 44px;
  height: 44px;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--primary), var(--accent));
  color: white;
  font-size: 0.82rem;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.conv-info { flex: 1; min-width: 0; }

.conv-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 3px;
}

.conv-nombre { font-size: 0.9rem; }
.conv-tiempo { font-size: 0.72rem; color: var(--text-light); }
.conv-preview { font-size: 0.82rem; color: var(--text-muted); white-space: nowrap; overflow: hidden; text-overflow: ellipsis; margin-bottom: 2px; }
.conv-vehiculo { font-size: 0.75rem; color: var(--text-light); }

.conv-badge {
  background: var(--primary);
  color: white;
  font-size: 0.7rem;
  font-weight: 700;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.empty-conversations { padding: 40px; text-align: center; color: var(--text-muted); font-size: 0.9rem; }

/* Main chat */
.chat-main {
  display: flex;
  flex-direction: column;
  height: 600px;
  overflow: hidden;
}

.chat-placeholder {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 12px;
  text-align: center;
  padding: 40px;
}

.placeholder-icon { font-size: 3rem; opacity: 0.4; }
.chat-placeholder h3 { font-size: 1.15rem; color: var(--text-muted); }
.chat-placeholder p { font-size: 0.88rem; color: var(--text-light); max-width: 280px; }

/* Chat header */
.chat-header {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 16px 20px;
  border-bottom: 1px solid var(--border-light);
}

.chat-header-avatar {
  width: 42px;
  height: 42px;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--primary), var(--accent));
  color: white;
  font-size: 0.82rem;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
}

.chat-header div:nth-child(2) { flex: 1; }
.chat-header strong { display: block; font-size: 0.97rem; }
.chat-header p { font-size: 0.78rem; color: var(--text-muted); margin: 0; }

.chat-header-actions { display: flex; gap: 8px; }

.chat-action-btn {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  border: 1px solid var(--border);
  background: var(--bg-card);
  cursor: pointer;
  font-size: 1rem;
  transition: var(--transition-fast);
  display: flex;
  align-items: center;
  justify-content: center;
}

.chat-action-btn:hover { background: var(--bg-alt); }

/* Messages */
.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  background: var(--bg-alt);
}

.msg-wrap {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.msg-wrap--propio { align-items: flex-end; }
.msg-wrap--otro  { align-items: flex-start; }

.msg-bubble {
  max-width: 70%;
  padding: 10px 15px;
  border-radius: 18px;
  font-size: 0.9rem;
  line-height: 1.5;
}

.msg-bubble--propio {
  background: var(--primary);
  color: white;
  border-bottom-right-radius: 4px;
}

.msg-bubble--otro {
  background: var(--bg-card);
  color: var(--text);
  border-bottom-left-radius: 4px;
  box-shadow: var(--shadow-xs);
}

.msg-hora { font-size: 0.7rem; color: var(--text-light); padding: 0 4px; }

/* Input */
.chat-input-area {
  display: flex;
  gap: 10px;
  padding: 14px 16px;
  border-top: 1px solid var(--border-light);
}

.chat-input {
  flex: 1;
  padding: 11px 16px;
  border: 1.5px solid var(--border);
  border-radius: var(--radius-pill);
  background: var(--bg-alt);
  font-size: 0.9rem;
  outline: none;
  transition: var(--transition-fast);
}

.chat-input:focus { border-color: var(--primary); background: var(--bg-card); }

.chat-send-btn {
  width: 42px;
  height: 42px;
  border-radius: 50%;
  background: var(--primary);
  color: white;
  border: none;
  font-size: 1rem;
  cursor: pointer;
  transition: var(--transition);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.chat-send-btn:hover:not(:disabled) { background: var(--primary-dark); transform: scale(1.05); }
.chat-send-btn:disabled { opacity: 0.4; cursor: not-allowed; }

@media (max-width: 768px) {
  .chat-layout { grid-template-columns: 1fr; height: auto; }
  .chat-sidebar, .chat-main { height: 400px; }
}
</style>
