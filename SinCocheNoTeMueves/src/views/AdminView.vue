<template>
  <div>
    <div class="page-header">
      <div class="container page-header-content">
        <h1>⚙️ Panel de Administración</h1>
        <p>Gestión completa de la plataforma</p>
      </div>
    </div>

    <div class="container admin-layout">

      <!-- Acceso denegado -->
      <div v-if="!esAdmin" class="card-base acceso-denegado">
        <div class="acceso-icon">🔒</div>
        <h2>Acceso restringido</h2>
        <p>Esta sección es exclusiva para administradores de la plataforma.</p>
        <router-link to="/" class="btn btn-primary">Volver al inicio</router-link>
      </div>

      <template v-else>

        <!-- Stats -->
        <div class="admin-stats">
          <div class="card-base admin-stat animate-up">
            <div class="astat-icon astat-blue">👥</div>
            <div>
              <span class="astat-num">{{ usuarios.length }}</span>
              <span class="astat-label">Usuarios</span>
            </div>
          </div>
          <div class="card-base admin-stat animate-up animate-up-1">
            <div class="astat-icon astat-red">🚫</div>
            <div>
              <span class="astat-num">{{ usuariosBloqueados }}</span>
              <span class="astat-label">Bloqueados</span>
            </div>
          </div>
          <div class="card-base admin-stat animate-up animate-up-2">
            <div class="astat-icon astat-orange">📋</div>
            <div>
              <span class="astat-num">{{ acciones.length }}</span>
              <span class="astat-label">Acciones</span>
            </div>
          </div>
          <div class="card-base admin-stat animate-up animate-up-3">
            <div class="astat-icon astat-green">✅</div>
            <div>
              <span class="astat-num">{{ usuarios.length - usuariosBloqueados }}</span>
              <span class="astat-label">Activos</span>
            </div>
          </div>
        </div>

        <!-- Gestión Usuarios -->
        <div class="card-base admin-card">
          <div class="admin-card-header">
            <h3>👥 Gestión de usuarios</h3>
            <div class="admin-search-wrap">
              <input v-model="busqueda" class="form-input admin-search" placeholder="Buscar por nombre, email o DNI..." />
            </div>
          </div>

          <div class="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th>Usuario</th>
                  <th>Email</th>
                  <th>DNI</th>
                  <th>Rol</th>
                  <th>Estado</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="u in usuariosFiltrados" :key="u.id" :class="{ 'fila-bloqueada': u.bloqueado }">
                  <td>
                    <div class="user-cell">
                      <div class="user-mini-avatar">{{ (u.nombre[0] || '') + (u.apellidos[0] || '') }}</div>
                      <span>{{ u.nombre }} {{ u.apellidos }}</span>
                    </div>
                  </td>
                  <td>{{ u.email }}</td>
                  <td>{{ u.dni }}</td>
                  <td>
                    <span class="badge" :class="u.rol === 'admin' ? 'badge-gold' : 'badge-blue'">
                      {{ u.rol === 'admin' ? '⭐ Admin' : '👤 User' }}
                    </span>
                  </td>
                  <td>
                    <span class="badge" :class="u.bloqueado ? 'badge-red' : 'badge-green'">
                      {{ u.bloqueado ? '🚫 Bloqueado' : '✅ Activo' }}
                    </span>
                  </td>
                  <td>
                    <div class="action-btns">
                      <button v-if="!u.bloqueado && u.rol !== 'admin'" class="btn-action btn-warn" @click="bloquearUsuario(u)">Bloquear</button>
                      <button v-if="u.bloqueado" class="btn-action btn-success" @click="desbloquearUsuario(u)">Desbloquear</button>
                      <button v-if="u.rol !== 'admin'" class="btn-action btn-danger" @click="eliminarUsuario(u)">Eliminar</button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Log Moderación -->
        <div class="card-base admin-card">
          <h3>📋 Registro de moderación</h3>

          <div v-if="acciones.length === 0" class="empty-state">
            <div class="empty-state-icon">📋</div>
            <h3>Sin acciones registradas</h3>
            <p>Las acciones de moderación aparecerán aquí</p>
          </div>

          <div v-else class="acciones-log">
            <div v-for="(a, i) in acciones" :key="i" class="log-item">
              <span class="log-tipo badge" :class="a.tipo === 'bloqueo' ? 'badge-gold' : a.tipo === 'desbloqueo' ? 'badge-green' : 'badge-red'">
                {{ a.tipo }}
              </span>
              <span class="log-desc">{{ a.descripcion }}</span>
              <span class="log-fecha">{{ a.fecha }}</span>
            </div>
          </div>
        </div>

      </template>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import api from '../services/api'

const esAdmin = ref(false)
const busqueda = ref('')
const acciones = ref([])
const usuarios = ref([])

const usuariosBloqueados = computed(() => usuarios.value.filter(u => u.bloqueado).length)

const usuariosFiltrados = computed(() => {
  const texto = busqueda.value.trim().toLowerCase()
  if (!texto) return usuarios.value
  return usuarios.value.filter(u =>
    u.nombre.toLowerCase().includes(texto) || u.apellidos.toLowerCase().includes(texto) ||
    u.email.toLowerCase().includes(texto) || u.dni.toLowerCase().includes(texto)
  )
})

onMounted(async () => {
  const datos = localStorage.getItem('usuario')
  if (datos) { const user = JSON.parse(datos); esAdmin.value = user.rol === 'admin' }
  if (esAdmin.value) {
    try { const res = await api.get('/admin/usuarios'); usuarios.value = res.data }
    catch { console.error('Error al cargar usuarios') }
  }
  acciones.value = JSON.parse(localStorage.getItem('moderacion')) || []
})

const ahora = () => new Date().toLocaleString('es-ES')
const registrarAccion = (tipo, descripcion) => {
  acciones.value.unshift({ tipo, descripcion, fecha: ahora() })
  localStorage.setItem('moderacion', JSON.stringify(acciones.value))
}

const bloquearUsuario = (u) => {
  if (!confirm(`¿Bloquear a ${u.nombre} ${u.apellidos}?`)) return
  u.bloqueado = true
  registrarAccion('bloqueo', `${u.nombre} ${u.apellidos} (${u.email}) bloqueado`)
}

const desbloquearUsuario = (u) => {
  u.bloqueado = false
  registrarAccion('desbloqueo', `${u.nombre} ${u.apellidos} (${u.email}) desbloqueado`)
}

const eliminarUsuario = async (u) => {
  if (!confirm(`¿Eliminar permanentemente a ${u.nombre} ${u.apellidos}?`)) return
  try {
    await api.delete('/admin/usuarios/' + u.id)
    registrarAccion('eliminación', `${u.nombre} ${u.apellidos} (${u.email}) eliminado`)
    usuarios.value = usuarios.value.filter(x => x.id !== u.id)
  } catch { alert('Error al eliminar') }
}
</script>

<style scoped>
.admin-layout {
  max-width: 1100px;
  margin: 0 auto;
  padding: 40px 16px 80px;
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.acceso-denegado {
  text-align: center;
  padding: 72px 32px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 14px;
}

.acceso-icon { font-size: 3rem; }
.acceso-denegado h2 { margin: 0; }
.acceso-denegado p { color: var(--text-muted); margin: 0; }

/* Stats */
.admin-stats {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.admin-stat {
  padding: 24px;
  display: flex;
  align-items: center;
  gap: 16px;
}

.astat-icon {
  width: 48px;
  height: 48px;
  border-radius: var(--radius-lg);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.3rem;
  flex-shrink: 0;
}

.astat-blue   { background: rgba(0,119,255,0.1); }
.astat-red    { background: rgba(239,68,68,0.1); }
.astat-orange { background: rgba(245,158,11,0.1); }
.astat-green  { background: rgba(16,185,129,0.1); }

.astat-num {
  display: block;
  font-family: var(--font-heading);
  font-size: 1.9rem;
  font-weight: 800;
  color: var(--primary);
  line-height: 1;
}

.astat-label {
  display: block;
  font-size: 0.8rem;
  color: var(--text-muted);
  margin-top: 3px;
}

/* Cards */
.admin-card { padding: 32px; }

.admin-card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 24px;
  flex-wrap: wrap;
  gap: 16px;
}

.admin-card-header h3 { font-size: 1.05rem; }

.admin-search { max-width: 300px; }

/* Tabla */
.user-cell { display: flex; align-items: center; gap: 10px; }

.user-mini-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--primary), var(--accent));
  color: white;
  font-size: 0.7rem;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.fila-bloqueada td { opacity: 0.5; }

.action-btns { display: flex; gap: 6px; }

.btn-action {
  padding: 5px 12px;
  border-radius: var(--radius-sm);
  border: none;
  font-size: 0.78rem;
  font-weight: 600;
  cursor: pointer;
  transition: var(--transition-fast);
}

.btn-warn { background: var(--warning-bg); color: #92400e; }
.btn-warn:hover { background: var(--warning); color: white; }

.btn-success { background: var(--success-bg); color: #065f46; }
.btn-success:hover { background: var(--success); color: white; }

.btn-danger { background: var(--danger-bg); color: #991b1b; }
.btn-danger:hover { background: var(--danger); color: white; }

/* Acciones log */
.acciones-log { display: flex; flex-direction: column; gap: 8px; }

.log-item {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 12px 16px;
  background: var(--bg-alt);
  border-radius: var(--radius-md);
  border: 1px solid var(--border-light);
  flex-wrap: wrap;
}

.log-desc { flex: 1; font-size: 0.88rem; color: var(--text); }
.log-fecha { font-size: 0.75rem; color: var(--text-light); flex-shrink: 0; }

@media (max-width: 900px) {
  .admin-stats { grid-template-columns: repeat(2, 1fr); }
}

@media (max-width: 600px) {
  .admin-stats { grid-template-columns: 1fr; }
  .admin-card-header { flex-direction: column; align-items: flex-start; }
  .admin-search { max-width: 100%; width: 100%; }
}
</style>
