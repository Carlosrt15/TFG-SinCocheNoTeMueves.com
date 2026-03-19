<template>
  <div class="admin-wrapper">

    <!-- Sin permisos -->
    <div v-if="!esAdmin" class="card aviso">
      <h2>Acceso denegado</h2>
      <p>Solo los administradores pueden acceder a este panel.</p>
      <router-link to="/"><button>Volver al inicio</button></router-link>
    </div>

    <!-- Panel admin -->
    <template v-else>

      <h2>Panel de Administración</h2>

      <!-- Resumen -->
      <div class="stats-grid">
        <div class="stat-card">
          <span class="stat-numero">{{ usuarios.length }}</span>
          <span class="stat-label">Usuarios registrados</span>
        </div>
        <div class="stat-card">
          <span class="stat-numero">{{ usuariosBloqueados }}</span>
          <span class="stat-label">Usuarios bloqueados</span>
        </div>
        <div class="stat-card">
          <span class="stat-numero">{{ acciones.length }}</span>
          <span class="stat-label">Acciones de moderación</span>
        </div>
      </div>

      <!-- Listado usuarios -->
      <div class="card">
        <h3>Gestión de usuarios</h3>

        <input
          v-model="busqueda"
          placeholder="Buscar por nombre, email o DNI..."
          class="buscador"
        />

        <div class="tabla-wrapper">
          <table>
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Email</th>
                <th>DNI</th>
                <th>Rol</th>
                <th>Estado</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="u in usuariosFiltrados" :key="u.id" :class="{ bloqueado: u.bloqueado }">
                <td>{{ u.nombre }} {{ u.apellidos }}</td>
                <td>{{ u.email }}</td>
                <td>{{ u.dni }}</td>
                <td>
                  <span class="badge" :class="u.rol">{{ u.rol }}</span>
                </td>
                <td>
                  <span class="estado" :class="u.bloqueado ? 'inactivo' : 'activo'">
                    {{ u.bloqueado ? 'Bloqueado' : 'Activo' }}
                  </span>
                </td>
                <td>
                  <div class="btn-group">
                    <button
                      v-if="!u.bloqueado && u.rol !== 'admin'"
                      class="btn-bloquear"
                      @click="bloquearUsuario(u)"
                    >
                      Bloquear
                    </button>
                    <button
                      v-if="u.bloqueado"
                      class="btn-desbloquear"
                      @click="desbloquearUsuario(u)"
                    >
                      Desbloquear
                    </button>
                    <button
                      v-if="u.rol !== 'admin'"
                      class="btn-eliminar"
                      @click="eliminarUsuario(u)"
                    >
                      Eliminar
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- Registro de acciones -->
      <div class="card">
        <h3>Registro de moderación</h3>

        <div v-if="acciones.length === 0" class="vacio">
          <p>No hay acciones de moderación registradas.</p>
        </div>

        <div v-else class="lista-acciones">
          <div v-for="(a, i) in acciones" :key="i" class="accion-item">
            <span class="accion-tipo" :class="a.tipo">{{ a.tipo }}</span>
            <span class="accion-texto">{{ a.descripcion }}</span>
            <span class="accion-fecha">{{ a.fecha }}</span>
          </div>
        </div>
      </div>

    </template>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'

const esAdmin = ref(false)
const busqueda = ref('')
const acciones = ref([])

const usuarios = ref([
  { id: 1, nombre: 'Carlos', apellidos: 'Rodríguez', email: 'carlos@email.com', dni: '12345678A', rol: 'user', telefono: '611111111', bloqueado: false },
  { id: 2, nombre: 'Mario', apellidos: 'Rodríguez', email: 'mario@email.com', dni: '23456789B', rol: 'user', telefono: '622222222', bloqueado: false },
  { id: 3, nombre: 'Daniel', apellidos: 'Barbero', email: 'daniel@email.com', dni: '34567890C', rol: 'admin', telefono: '633333333', bloqueado: false },
  { id: 4, nombre: 'Laura', apellidos: 'Martín', email: 'laura@email.com', dni: '45678901D', rol: 'user', telefono: '644444444', bloqueado: false },
  { id: 5, nombre: 'Pedro', apellidos: 'Sánchez', email: 'pedro@email.com', dni: '56789012E', rol: 'user', telefono: '655555555', bloqueado: true }
])

const usuariosBloqueados = computed(() =>
  usuarios.value.filter(u => u.bloqueado).length
)

const usuariosFiltrados = computed(() => {
  const texto = busqueda.value.trim().toLowerCase()
  if (!texto) return usuarios.value
  return usuarios.value.filter(u =>
    u.nombre.toLowerCase().includes(texto) ||
    u.apellidos.toLowerCase().includes(texto) ||
    u.email.toLowerCase().includes(texto) ||
    u.dni.toLowerCase().includes(texto)
  )
})

onMounted(() => {
  const datos = localStorage.getItem('usuario')
  if (datos) {
    const user = JSON.parse(datos)
    esAdmin.value = user.rol === 'admin'
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
  registrarAccion('bloqueo', `Usuario ${u.nombre} ${u.apellidos} (${u.email}) bloqueado`)
}

const desbloquearUsuario = (u) => {
  u.bloqueado = false
  registrarAccion('desbloqueo', `Usuario ${u.nombre} ${u.apellidos} (${u.email}) desbloqueado`)
}

const eliminarUsuario = (u) => {
  if (!confirm(`¿Eliminar permanentemente a ${u.nombre} ${u.apellidos}? Esta acción no se puede deshacer.`)) return
  registrarAccion('eliminación', `Usuario ${u.nombre} ${u.apellidos} (${u.email}) eliminado`)
  usuarios.value = usuarios.value.filter(x => x.id !== u.id)
}
</script>

<style scoped>
.admin-wrapper {
  max-width: 1100px;
  margin: 0 auto;
  padding: 40px 20px;
}

.card {
  background: white;
  border-radius: 16px;
  padding: 30px;
  margin-bottom: 25px;
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.07);
}

.aviso {
  text-align: center;
  padding: 60px 30px;
}

.aviso p {
  color: #666;
  margin-bottom: 20px;
}

/* Stats */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 25px;
}

.stat-card {
  background: white;
  border-radius: 14px;
  padding: 25px;
  text-align: center;
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.06);
}

.stat-numero {
  display: block;
  font-size: 32px;
  font-weight: 700;
  color: #0077ff;
}

.stat-label {
  display: block;
  margin-top: 6px;
  color: #888;
  font-size: 14px;
}

/* Buscador */
.buscador {
  width: 100%;
  max-width: 400px;
  padding: 10px 14px;
  border: 1px solid #ddd;
  border-radius: 8px;
  margin-bottom: 20px;
}

/* Tabla */
h3 {
  margin-top: 0;
  margin-bottom: 20px;
}

.tabla-wrapper {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  text-align: left;
  padding: 12px 14px;
  border-bottom: 1px solid #eee;
  white-space: nowrap;
}

th {
  background: #f8f9fa;
  font-size: 13px;
  color: #555;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

tr.bloqueado {
  opacity: 0.55;
}

.badge {
  display: inline-block;
  padding: 3px 10px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
}

.badge.admin {
  background: #fff3cd;
  color: #856404;
}

.badge.user {
  background: #d1ecf1;
  color: #0c5460;
}

.estado {
  font-weight: 600;
  font-size: 13px;
}

.estado.activo {
  color: #28a745;
}

.estado.inactivo {
  color: #dc3545;
}

/* Botones acción */
.btn-group {
  display: flex;
  gap: 6px;
}

.btn-bloquear {
  background: #ffc107;
  color: #333;
  font-size: 12px;
  padding: 6px 10px;
}

.btn-bloquear:hover {
  background: #e0a800;
}

.btn-desbloquear {
  background: #28a745;
  font-size: 12px;
  padding: 6px 10px;
}

.btn-desbloquear:hover {
  background: #218838;
}

.btn-eliminar {
  background: #dc3545;
  font-size: 12px;
  padding: 6px 10px;
}

.btn-eliminar:hover {
  background: #c82333;
}

/* Registro acciones */
.lista-acciones {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.accion-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 14px;
  background: #f8f9fa;
  border-radius: 8px;
  flex-wrap: wrap;
}

.accion-tipo {
  display: inline-block;
  padding: 3px 10px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
  text-transform: capitalize;
  flex-shrink: 0;
}

.accion-tipo.bloqueo {
  background: #fff3cd;
  color: #856404;
}

.accion-tipo.desbloqueo {
  background: #d4edda;
  color: #155724;
}

.accion-tipo.eliminación {
  background: #f8d7da;
  color: #721c24;
}

.accion-texto {
  flex: 1;
  font-size: 14px;
  color: #333;
}

.accion-fecha {
  font-size: 12px;
  color: #999;
  flex-shrink: 0;
}

.vacio {
  text-align: center;
  color: #999;
  padding: 20px;
}

@media (max-width: 768px) {
  .stats-grid {
    grid-template-columns: 1fr;
  }

  .btn-group {
    flex-direction: column;
  }

  .accion-item {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>