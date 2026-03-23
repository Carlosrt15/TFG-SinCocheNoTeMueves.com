<template>
  <div>
    <div class="page-header">
      <div class="container page-header-content">
        <h1>♥ Mis Favoritos</h1>
        <p>{{ favoritos.length }} vehículo{{ favoritos.length !== 1 ? 's' : '' }} guardado{{ favoritos.length !== 1 ? 's' : '' }}</p>
      </div>
    </div>

    <div class="container" style="padding: 40px 16px 80px">
      <div v-if="favoritos.length === 0" class="empty-state">
        <div class="empty-state-icon">♥</div>
        <h3>Sin favoritos todavía</h3>
        <p>Explora el catálogo y guarda los vehículos que más te gusten</p>
        <router-link to="/vehiculos" class="btn btn-primary" style="margin-top: 16px">Ver catálogo</router-link>
      </div>

      <div v-else class="favs-grid">
        <div v-for="f in favoritos" :key="f.id" class="fav-card animate-up">
          <div class="fav-img-wrap">
            <img :src="f.imagen || 'https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?w=600&q=80'" :alt="`${f.marca} ${f.modelo}`" />
          </div>
          <div class="fav-info">
            <div>
              <h3 class="fav-title">{{ f.marca }} {{ f.modelo }}</h3>
              <p class="fav-precio">{{ f.precio?.toLocaleString('es-ES') }} €</p>
            </div>
            <div class="fav-actions">
              <router-link :to="'/vehiculo/' + f.id" class="btn btn-primary btn-sm">Ver detalle</router-link>
              <button class="btn btn-outline btn-sm btn-eliminar" @click="eliminar(f.id)">🗑️</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const favoritos = ref([])

const cargarFavoritos = () => {
  favoritos.value = JSON.parse(localStorage.getItem('favoritos')) || []
}

onMounted(() => cargarFavoritos())

const eliminar = (id) => {
  favoritos.value = favoritos.value.filter(f => f.id !== id)
  localStorage.setItem('favoritos', JSON.stringify(favoritos.value))
  window.dispatchEvent(new Event('storage'))
}
</script>

<style scoped>
.favs-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 24px;
}

.fav-card {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  overflow: hidden;
  border: 1px solid var(--border-light);
  box-shadow: var(--shadow-sm);
  transition: var(--transition-slow);
  display: flex;
  flex-direction: column;
}

.fav-card:hover {
  transform: translateY(-5px);
  box-shadow: var(--shadow-md);
}

.fav-img-wrap {
  height: 200px;
  overflow: hidden;
  background: var(--bg-alt);
}

.fav-img-wrap img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.fav-card:hover .fav-img-wrap img { transform: scale(1.05); }

.fav-info {
  padding: 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  flex-wrap: wrap;
}

.fav-title {
  font-size: 1rem;
  font-weight: 700;
  margin-bottom: 4px;
}

.fav-precio {
  font-family: var(--font-heading);
  font-size: 1.15rem;
  font-weight: 800;
  color: var(--primary);
}

.fav-actions {
  display: flex;
  gap: 8px;
}

.btn-sm { padding: 9px 16px; font-size: 0.85rem; }

.btn-eliminar {
  padding: 9px 12px;
  color: var(--danger);
  border-color: var(--danger);
  border-radius: var(--radius-md);
}

.btn-eliminar:hover {
  background: var(--danger);
  color: white;
}
</style>
