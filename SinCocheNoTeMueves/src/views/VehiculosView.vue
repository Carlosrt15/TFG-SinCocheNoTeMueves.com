<template>
  <div>
    <!-- Page Header -->
    <div class="page-header">
      <div class="container page-header-content">
        <h1>🚘 Catálogo de Vehículos</h1>
        <p>{{ vehiculos.length }} vehículos disponibles — encuentra el tuyo</p>
      </div>
    </div>

    <div class="container vehiculos-layout">

      <!-- Sidebar Filtros -->
      <aside class="sidebar">
        <div class="sidebar-card">
          <h3 class="sidebar-title">🔍 Filtrar</h3>

          <div class="form-group">
            <label class="form-label">Buscar</label>
            <input v-model="busqueda" class="form-input" placeholder="Marca, modelo..." />
          </div>

          <div class="form-group">
            <label class="form-label">Precio máximo</label>
            <input v-model.number="precioMax" type="range" min="0" max="200000" step="1000" class="range-input" />
            <div class="range-value">
              <span>0 €</span>
              <span class="range-current">{{ precioMax ? precioMax.toLocaleString('es-ES') + ' €' : 'Sin límite' }}</span>
            </div>
          </div>

          <div class="form-group">
            <label class="form-label">Estado</label>
            <div class="filter-chips">
              <button class="chip" :class="{ 'chip--active': estadoFiltro === '' }" @click="estadoFiltro = ''">Todos</button>
              <button class="chip" :class="{ 'chip--active': estadoFiltro === 'Nuevo' }" @click="estadoFiltro = 'Nuevo'">Nuevo</button>
              <button class="chip" :class="{ 'chip--active': estadoFiltro === 'Usado' }" @click="estadoFiltro = 'Usado'">Usado</button>
            </div>
          </div>

          <button class="btn btn-outline btn-reset" @click="resetFiltros">Limpiar filtros</button>
        </div>
      </aside>

      <!-- Contenido Principal -->
      <main class="vehiculos-main">

        <!-- Toolbar -->
        <div class="toolbar">
          <span class="toolbar-count">
            <strong>{{ filtrados.length }}</strong> resultados
          </span>
          <div class="toolbar-view">
            <button class="view-btn" :class="{ active: vista === 'grid' }" @click="vista = 'grid'">▦</button>
            <button class="view-btn" :class="{ active: vista === 'list' }" @click="vista = 'list'">☰</button>
          </div>
        </div>

        <!-- Loading -->
        <div v-if="cargando" class="loading-grid">
          <div v-for="i in 6" :key="i" class="skeleton-card"></div>
        </div>

        <!-- Sin resultados -->
        <div v-else-if="filtrados.length === 0" class="empty-state">
          <div class="empty-state-icon">🔍</div>
          <h3>Sin resultados</h3>
          <p>Prueba con otros filtros o términos de búsqueda</p>
          <button class="btn btn-primary" @click="resetFiltros">Ver todos</button>
        </div>

        <!-- Grid de vehículos -->
        <div v-else :class="vista === 'grid' ? 'vehicles-grid' : 'vehicles-list'">
          <VehiculoCard v-for="v in filtrados" :key="v.id" :vehiculo="v" />
        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import VehiculoCard from '../components/VehiculoCard.vue'
import api from '../services/api'

const busqueda = ref('')
const precioMax = ref(null)
const estadoFiltro = ref('')
const vista = ref('grid')
const vehiculos = ref([])
const cargando = ref(true)

onMounted(async () => {
  try {
    const res = await api.get('/vehiculos')
    vehiculos.value = res.data
  } catch (error) {
    console.log(error)
  } finally {
    cargando.value = false
  }
})

const filtrados = computed(() => {
  let result = vehiculos.value
  const texto = busqueda.value.toLowerCase()
  if (texto) result = result.filter(v => v.marca.toLowerCase().includes(texto) || v.modelo.toLowerCase().includes(texto))
  if (precioMax.value) result = result.filter(v => v.precio <= precioMax.value)
  if (estadoFiltro.value) result = result.filter(v => v.estado === estadoFiltro.value)
  return result
})

const resetFiltros = () => {
  busqueda.value = ''
  precioMax.value = null
  estadoFiltro.value = ''
}
</script>

<style scoped>
.vehiculos-layout {
  display: grid;
  grid-template-columns: 280px 1fr;
  gap: 32px;
  padding: 40px 16px 60px;
  align-items: start;
}

/* Sidebar */
.sidebar-card {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 28px 24px;
  border: 1px solid var(--border-light);
  box-shadow: var(--shadow-sm);
  position: sticky;
  top: calc(var(--navbar-h) + 20px);
}

.sidebar-title {
  font-size: 1.05rem;
  margin-bottom: 24px;
  padding-bottom: 14px;
  border-bottom: 1px solid var(--border-light);
}

.range-input {
  width: 100%;
  accent-color: var(--primary);
  height: 4px;
  cursor: pointer;
}

.range-value {
  display: flex;
  justify-content: space-between;
  font-size: 0.78rem;
  color: var(--text-muted);
  margin-top: 8px;
}

.range-current { color: var(--primary); font-weight: 600; }

.filter-chips { display: flex; gap: 8px; flex-wrap: wrap; }

.chip {
  padding: 6px 14px;
  border-radius: var(--radius-pill);
  border: 1.5px solid var(--border);
  background: var(--bg-card);
  color: var(--text-muted);
  font-size: 0.82rem;
  font-weight: 600;
  cursor: pointer;
  transition: var(--transition-fast);
}

.chip:hover { border-color: var(--primary); color: var(--primary); }
.chip--active { background: var(--primary); border-color: var(--primary); color: white; }

.btn-reset { width: 100%; margin-top: 8px; justify-content: center; }

/* Toolbar */
.toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 24px;
}

.toolbar-count { font-size: 0.9rem; color: var(--text-muted); }
.toolbar-count strong { color: var(--text); font-weight: 700; }

.toolbar-view { display: flex; gap: 4px; }

.view-btn {
  width: 36px;
  height: 36px;
  border-radius: var(--radius-sm);
  border: 1.5px solid var(--border);
  background: var(--bg-card);
  font-size: 1rem;
  cursor: pointer;
  transition: var(--transition-fast);
  color: var(--text-muted);
  display: flex;
  align-items: center;
  justify-content: center;
}

.view-btn.active { background: var(--primary); border-color: var(--primary); color: white; }

/* Grids */
.vehicles-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
}

.vehicles-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

/* Skeletons */
.loading-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
}

.skeleton-card {
  height: 340px;
  border-radius: var(--radius-lg);
  background: linear-gradient(90deg, var(--bg-alt) 25%, var(--border-light) 50%, var(--bg-alt) 75%);
  background-size: 200% 100%;
  animation: shimmer 1.5s infinite;
}

@keyframes shimmer {
  0%   { background-position: -200% center; }
  100% { background-position: 200% center; }
}

@media (max-width: 900px) {
  .vehiculos-layout { grid-template-columns: 1fr; }
  .sidebar { order: -1; }
  .sidebar-card { position: static; }
}
</style>
