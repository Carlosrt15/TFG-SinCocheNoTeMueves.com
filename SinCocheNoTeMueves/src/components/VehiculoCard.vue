<template>
  <div class="vcard animate-up">
    <!-- Image -->
    <div class="vcard-img-wrap">
      <img :src="vehiculo.imagen || 'https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?w=600&q=80'" :alt="`${vehiculo.marca} ${vehiculo.modelo}`" />
      <div class="vcard-overlay">
        <button class="fav-btn" @click.prevent="agregarFavorito" :title="'Añadir a favoritos'">
          ♥
        </button>
      </div>
      <span class="vcard-estado" :class="vehiculo.estado === 'Nuevo' ? 'estado-nuevo' : 'estado-usado'">
        {{ vehiculo.estado || 'Disponible' }}
      </span>
    </div>

    <!-- Info -->
    <div class="vcard-info">
      <div class="vcard-header">
        <h3 class="vcard-title">{{ vehiculo.marca }} {{ vehiculo.modelo }}</h3>
        <span class="vcard-precio">{{ vehiculo.precio?.toLocaleString('es-ES') }} €</span>
      </div>

      <div class="vcard-meta">
        <span v-if="vehiculo.anio" class="meta-chip">📅 {{ vehiculo.anio }}</span>
        <span v-if="vehiculo.km" class="meta-chip">🛣️ {{ vehiculo.km?.toLocaleString() }} km</span>
        <span class="meta-chip">⚡ {{ vehiculo.combustible || 'Gasolina' }}</span>
      </div>

      <div class="vcard-actions">
        <router-link :to="'/vehiculo/' + vehiculo.id" class="btn btn-primary btn-sm">
          Ver detalle →
        </router-link>
        <button class="btn btn-outline btn-sm btn-fav" @click="agregarFavorito" title="Favorito">
          ♥
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import api from '../services/api'

const props = defineProps({
  vehiculo: { type: Object, required: true }
})

const guardarEnLocalStorage = (v) => {
  // Sincroniza también con la lista local que lee FavoritosView.vue
  const favoritos = JSON.parse(localStorage.getItem('favoritos') || '[]')
  if (!favoritos.find(f => f.id === v.id)) {
    favoritos.push(v)
    localStorage.setItem('favoritos', JSON.stringify(favoritos))
    window.dispatchEvent(new Event('storage'))
  }
}

const agregarFavorito = async () => {
  // Guardamos siempre en local para feedback inmediato (UX)
  guardarEnLocalStorage(props.vehiculo)

  // Si el usuario está autenticado, también lo persistimos en backend
  const token = localStorage.getItem('token')
  if (token) {
    try {
      await api.post('/favoritos/' + props.vehiculo.id)
    } catch (err) {
      // Silencioso: el favorito ya está guardado localmente
      console.warn('No se pudo sincronizar el favorito con el servidor', err)
    }
  }
}
</script>

<style scoped>
.vcard {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  overflow: hidden;
  box-shadow: var(--shadow-card);
  border: 1px solid var(--border-light);
  transition: var(--transition-slow);
  display: flex;
  flex-direction: column;
}

.vcard:hover {
  transform: translateY(-6px);
  box-shadow: var(--shadow-lg);
  border-color: transparent;
}

/* Image */
.vcard-img-wrap {
  position: relative;
  overflow: hidden;
  height: 210px;
  background: var(--bg-alt);
}

.vcard-img-wrap img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.vcard:hover .vcard-img-wrap img {
  transform: scale(1.06);
}

.vcard-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(to top, rgba(0,0,0,0.4) 0%, transparent 50%);
  opacity: 0;
  transition: var(--transition);
  display: flex;
  align-items: flex-start;
  justify-content: flex-end;
  padding: 12px;
}

.vcard:hover .vcard-overlay { opacity: 1; }

.fav-btn {
  background: rgba(255,255,255,0.2);
  backdrop-filter: blur(8px);
  border: 1px solid rgba(255,255,255,0.3);
  color: white;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  cursor: pointer;
  font-size: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: var(--transition);
  padding: 0;
}

.fav-btn:hover {
  background: #ef4444;
  border-color: #ef4444;
  transform: scale(1.1);
}

.vcard-estado {
  position: absolute;
  top: 12px;
  left: 12px;
  font-size: 0.72rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.07em;
  padding: 4px 10px;
  border-radius: var(--radius-pill);
}

.estado-nuevo { background: var(--success); color: white; }
.estado-usado { background: var(--navy-mid); color: white; }

/* Info */
.vcard-info {
  padding: 18px 20px 20px;
  display: flex;
  flex-direction: column;
  flex: 1;
  gap: 12px;
}

.vcard-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 8px;
}

.vcard-title {
  font-size: 1.05rem;
  font-weight: 700;
  color: var(--text);
  line-height: 1.3;
}

.vcard-precio {
  font-family: var(--font-heading);
  font-size: 1.2rem;
  font-weight: 800;
  color: var(--primary);
  white-space: nowrap;
}

.vcard-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

.meta-chip {
  font-size: 0.75rem;
  color: var(--text-muted);
  background: var(--bg-alt);
  padding: 4px 10px;
  border-radius: var(--radius-pill);
  font-weight: 500;
}

/* Actions */
.vcard-actions {
  display: flex;
  gap: 8px;
  margin-top: auto;
}

.btn-sm {
  padding: 9px 18px;
  font-size: 0.85rem;
  flex: 1;
}

.btn-fav {
  flex: 0;
  width: 40px;
  padding: 9px;
  color: var(--danger);
  border-color: var(--danger);
  border-radius: var(--radius-md);
}

.btn-fav:hover {
  background: var(--danger);
  color: white;
}
</style>
