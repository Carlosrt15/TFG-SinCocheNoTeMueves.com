<template>
  <div>
    <div class="page-header">
      <div class="container page-header-content">
        <h1>👤 Mi Perfil</h1>
        <p>Gestiona tu cuenta y revisa tu historial</p>
      </div>
    </div>

    <div class="container perfil-layout">

      <!-- Sin sesión -->
      <div v-if="!usuario" class="card-base no-sesion">
        <div class="no-sesion-icon">🔐</div>
        <h2>Sesión no iniciada</h2>
        <p>Inicia sesión para acceder a tu perfil, historial de compras y más.</p>
        <router-link to="/login" class="btn btn-primary">Iniciar sesión</router-link>
      </div>

      <template v-else>

        <!-- Cabecera Perfil -->
        <div class="card-base perfil-cabecera">
          <div class="perfil-avatar">{{ iniciales }}</div>
          <div class="perfil-usuario-info">
            <h2>{{ usuario.nombre }} {{ usuario.apellidos }}</h2>
            <p class="perfil-email">{{ usuario.email }}</p>
            <span class="badge" :class="usuario.rol === 'ADMIN' ? 'badge-gold' : 'badge-blue'">
              {{ usuario.rol === 'ADMIN' ? '⭐ Administrador' : '👤 Usuario' }}
            </span>
          </div>
          <button class="btn btn-danger btn-logout" @click="cerrarSesion">Cerrar sesión</button>
        </div>

        <!-- Stats -->
        <div class="perfil-stats">
          <div class="card-base stat-card animate-up">
            <div class="stat-icon stat-icon--blue">🛒</div>
            <div class="stat-info">
              <span class="stat-num">{{ compras.length }}</span>
              <span class="stat-label">Compras realizadas</span>
            </div>
          </div>
          <div class="card-base stat-card animate-up animate-up-1">
            <div class="stat-icon stat-icon--red">♥</div>
            <div class="stat-info">
              <span class="stat-num">{{ favoritos.length }}</span>
              <span class="stat-label">Favoritos guardados</span>
            </div>
          </div>
          <div class="card-base stat-card animate-up animate-up-2">
            <div class="stat-icon stat-icon--green">💶</div>
            <div class="stat-info">
              <span class="stat-num">{{ totalGastado }}</span>
              <span class="stat-label">Total invertido</span>
            </div>
          </div>
        </div>

        <!-- Datos Personales -->
        <div class="card-base perfil-datos">
          <div class="perfil-section-header">
            <h3>Datos personales</h3>
            <button v-if="!editando" class="btn btn-outline btn-sm" @click="editando = true">✏️ Editar</button>
            <div v-else class="edit-btns">
              <button class="btn btn-primary btn-sm" @click="guardarCambios">💾 Guardar</button>
              <button class="btn btn-outline btn-sm" @click="cancelarEdicion">Cancelar</button>
            </div>
          </div>

          <div class="datos-grid">
            <div class="dato-item">
              <label class="form-label">Nombre</label>
              <input v-model="edicion.nombre" class="form-input" :disabled="!editando" />
            </div>
            <div class="dato-item">
              <label class="form-label">Apellidos</label>
              <input v-model="edicion.apellidos" class="form-input" :disabled="!editando" />
            </div>
            <div class="dato-item">
              <label class="form-label">DNI</label>
              <input v-model="edicion.dni" class="form-input" disabled />
            </div>
            <div class="dato-item">
              <label class="form-label">Teléfono</label>
              <input v-model="edicion.telefono" class="form-input" :disabled="!editando" />
            </div>
            <div class="dato-item dato-full">
              <label class="form-label">Email</label>
              <input v-model="edicion.email" class="form-input" disabled />
            </div>
          </div>
        </div>

        <!-- Historial Compras -->
        <div class="card-base historial">
          <h3>Historial de compras</h3>

          <div v-if="compras.length === 0" class="empty-state" style="padding: 40px 0">
            <div class="empty-state-icon">🛒</div>
            <h3>Aún no has comprado</h3>
            <p>Explora el catálogo y encuentra tu próximo vehículo</p>
            <router-link to="/vehiculos" class="btn btn-primary" style="margin-top:14px">Ver catálogo</router-link>
          </div>

          <div v-else class="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th>Vehículo</th>
                  <th>Precio</th>
                  <th>Fecha</th>
                  <th>Estado</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="c in compras" :key="c.id">
                  <td><strong>{{ c.marca }} {{ c.modelo }}</strong></td>
                  <td class="precio-cell">{{ c.precio?.toLocaleString('es-ES') }} €</td>
                  <td>{{ c.fecha }}</td>
                  <td><span class="badge badge-green">✅ Completada</span></td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

      </template>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const usuario = ref(null)
const compras = ref([])
const favoritos = ref([])
const editando = ref(false)

const edicion = reactive({ nombre: '', apellidos: '', dni: '', telefono: '', email: '' })

const iniciales = computed(() => {
  if (!usuario.value) return ''
  return ((usuario.value.nombre?.[0] || '') + (usuario.value.apellidos?.[0] || '')).toUpperCase()
})

const totalGastado = computed(() => {
  const total = compras.value.reduce((sum, c) => sum + (c.precio || 0), 0)
  return total.toLocaleString('es-ES') + ' €'
})

onMounted(() => {
  const datos = localStorage.getItem('usuario')
  if (datos) {
    usuario.value = JSON.parse(datos)
    Object.assign(edicion, { nombre: usuario.value.nombre || '', apellidos: usuario.value.apellidos || '', dni: usuario.value.dni || '', telefono: usuario.value.telefono || '', email: usuario.value.email || '' })
  }
  compras.value = JSON.parse(localStorage.getItem('compras')) || []
  favoritos.value = JSON.parse(localStorage.getItem('favoritos')) || []
})

const guardarCambios = () => {
  usuario.value.nombre = edicion.nombre
  usuario.value.apellidos = edicion.apellidos
  usuario.value.telefono = edicion.telefono
  localStorage.setItem('usuario', JSON.stringify(usuario.value))
  editando.value = false
}

const cancelarEdicion = () => {
  edicion.nombre = usuario.value.nombre
  edicion.apellidos = usuario.value.apellidos
  edicion.telefono = usuario.value.telefono
  editando.value = false
}

const cerrarSesion = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('usuario')
  usuario.value = null
  router.push('/')
}
</script>

<style scoped>
.perfil-layout {
  max-width: 960px;
  margin: 0 auto;
  padding: 40px 16px 80px;
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.no-sesion {
  text-align: center;
  padding: 72px 32px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 14px;
}

.no-sesion-icon { font-size: 3rem; }
.no-sesion h2 { margin: 0; }
.no-sesion p { color: var(--text-muted); margin: 0; }

/* Cabecera */
.perfil-cabecera {
  padding: 32px;
  display: flex;
  align-items: center;
  gap: 24px;
  flex-wrap: wrap;
}

.perfil-avatar {
  width: 72px;
  height: 72px;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--primary), var(--accent));
  color: white;
  font-family: var(--font-heading);
  font-size: 1.6rem;
  font-weight: 800;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  box-shadow: var(--shadow-blue);
}

.perfil-usuario-info { flex: 1; }
.perfil-usuario-info h2 { margin-bottom: 4px; font-size: 1.4rem; }
.perfil-email { color: var(--text-muted); font-size: 0.9rem; margin-bottom: 8px; }

.btn-logout { flex-shrink: 0; }

/* Stats */
.perfil-stats {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.stat-card {
  padding: 28px 24px;
  display: flex;
  align-items: center;
  gap: 20px;
}

.stat-icon {
  width: 52px;
  height: 52px;
  border-radius: var(--radius-lg);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.4rem;
  flex-shrink: 0;
}

.stat-icon--blue  { background: rgba(0,119,255,0.1); }
.stat-icon--red   { background: rgba(239,68,68,0.1); }
.stat-icon--green { background: rgba(16,185,129,0.1); }

.stat-info { display: flex; flex-direction: column; gap: 3px; }

.stat-num {
  font-family: var(--font-heading);
  font-size: 1.8rem;
  font-weight: 800;
  color: var(--primary);
  line-height: 1;
}

.stat-label { font-size: 0.82rem; color: var(--text-muted); }

/* Datos */
.perfil-datos { padding: 32px; }

.perfil-section-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 24px;
}

.perfil-section-header h3 { font-size: 1.05rem; }

.edit-btns { display: flex; gap: 8px; }

.datos-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.dato-item { display: flex; flex-direction: column; gap: 6px; }
.dato-full { grid-column: 1 / -1; }

/* Historial */
.historial { padding: 32px; }
.historial h3 { font-size: 1.05rem; margin-bottom: 24px; }

.precio-cell { font-weight: 700; color: var(--primary); }

.btn-sm { padding: 8px 16px; font-size: 0.85rem; }

@media (max-width: 768px) {
  .perfil-stats { grid-template-columns: 1fr; }
  .datos-grid { grid-template-columns: 1fr; }
  .dato-full { grid-column: 1; }
  .perfil-cabecera { flex-direction: column; text-align: center; }
  .btn-logout { width: 100%; }
}
</style>
