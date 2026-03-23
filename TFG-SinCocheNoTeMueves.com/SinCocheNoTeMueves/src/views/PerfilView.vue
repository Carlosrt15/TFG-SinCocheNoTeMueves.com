<template>
  <div class="perfil-wrapper">

    <!-- Sin sesión -->
    <div v-if="!usuario" class="card aviso">
      <h2>No has iniciado sesión</h2>
      <p>Inicia sesión para ver tu perfil y estadísticas.</p>
      <router-link to="/login"><button>Iniciar sesión</button></router-link>
    </div>

    <!-- Con sesión -->
    <template v-else>

      <!-- Cabecera perfil -->
      <div class="card cabecera">
        <div class="avatar">{{ iniciales }}</div>
        <div class="info-usuario">
          <h2>{{ usuario.nombre }} {{ usuario.apellidos }}</h2>
          <p class="email">{{ usuario.email }}</p>
          <p class="rol" :class="usuario.rol">{{ usuario.rol === 'admin' ? 'Administrador' : 'Usuario' }}</p>
        </div>
        <button class="btn-logout" @click="cerrarSesion">Cerrar sesión</button>
      </div>

      <!-- Estadísticas -->
      <div class="stats-grid">
        <div class="stat-card">
          <span class="stat-numero">{{ compras.length }}</span>
          <span class="stat-label">Compras realizadas</span>
        </div>
        <div class="stat-card">
          <span class="stat-numero">{{ favoritos.length }}</span>
          <span class="stat-label">Vehículos en favoritos</span>
        </div>
        <div class="stat-card">
          <span class="stat-numero">{{ totalGastado }}</span>
          <span class="stat-label">Total gastado</span>
        </div>
      </div>

      <!-- Datos personales -->
      <div class="card">
        <h3>Datos personales</h3>

        <div class="form-grid">
          <div class="campo">
            <label>Nombre</label>
            <input v-model="edicion.nombre" :disabled="!editando" />
          </div>
          <div class="campo">
            <label>Apellidos</label>
            <input v-model="edicion.apellidos" :disabled="!editando" />
          </div>
          <div class="campo">
            <label>DNI</label>
            <input v-model="edicion.dni" disabled />
          </div>
          <div class="campo">
            <label>Teléfono</label>
            <input v-model="edicion.telefono" :disabled="!editando" />
          </div>
          <div class="campo full">
            <label>Email</label>
            <input v-model="edicion.email" disabled />
          </div>
        </div>

        <div class="acciones">
          <button v-if="!editando" @click="editando = true">Editar perfil</button>
          <template v-else>
            <button @click="guardarCambios">Guardar</button>
            <button class="btn-cancelar" @click="cancelarEdicion">Cancelar</button>
          </template>
        </div>
      </div>

      <!-- Historial de compras -->
      <div class="card">
        <h3>Historial de compras</h3>

        <div v-if="compras.length === 0" class="vacio">
          <p>Aún no has realizado ninguna compra.</p>
        </div>

        <div v-else class="tabla-wrapper">
          <table>
            <thead>
              <tr>
                <th>Vehículo</th>
                <th>Precio</th>
                <th>Fecha</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="c in compras" :key="c.id">
                <td>{{ c.marca }} {{ c.modelo }}</td>
                <td>{{ c.precio.toLocaleString() }} €</td>
                <td>{{ c.fecha }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

    </template>
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

const edicion = reactive({
  nombre: '',
  apellidos: '',
  dni: '',
  telefono: '',
  email: ''
})

const iniciales = computed(() => {
  if (!usuario.value) return ''
  const n = usuario.value.nombre?.[0] || ''
  const a = usuario.value.apellidos?.[0] || ''
  return (n + a).toUpperCase()
})

const totalGastado = computed(() => {
  const total = compras.value.reduce((sum, c) => sum + (c.precio || 0), 0)
  return total.toLocaleString() + ' €'
})

onMounted(() => {
  const datos = localStorage.getItem('usuario')
  if (datos) {
    usuario.value = JSON.parse(datos)
    Object.assign(edicion, {
      nombre: usuario.value.nombre || '',
      apellidos: usuario.value.apellidos || '',
      dni: usuario.value.dni || '',
      telefono: usuario.value.telefono || '',
      email: usuario.value.email || ''
    })
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
.perfil-wrapper {
  max-width: 900px;
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

/* Cabecera */
.cabecera {
  display: flex;
  align-items: center;
  gap: 20px;
  flex-wrap: wrap;
}

.avatar {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  background: linear-gradient(135deg, #0077ff, #00c3ff);
  color: white;
  font-size: 26px;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.info-usuario {
  flex: 1;
}

.info-usuario h2 {
  margin: 0;
  text-align: left;
}

.email {
  color: #666;
  margin: 4px 0 0;
}

.rol {
  display: inline-block;
  margin-top: 6px;
  padding: 3px 12px;
  border-radius: 20px;
  font-size: 13px;
  font-weight: 600;
}

.rol.admin {
  background: #fff3cd;
  color: #856404;
}

.rol.user {
  background: #d1ecf1;
  color: #0c5460;
}

.btn-logout {
  background: #e74c3c;
  flex-shrink: 0;
}

.btn-logout:hover {
  background: #c0392b;
}

/* Estadísticas */
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

/* Formulario datos */
h3 {
  margin-top: 0;
  margin-bottom: 20px;
}

.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 15px;
}

.campo label {
  display: block;
  font-size: 13px;
  color: #555;
  margin-bottom: 5px;
  font-weight: 600;
}

.campo input {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  margin-bottom: 0;
}

.campo input:disabled {
  background: #f8f9fa;
  color: #666;
}

.campo.full {
  grid-column: 1 / -1;
}

.acciones {
  margin-top: 20px;
  display: flex;
  gap: 10px;
}

.btn-cancelar {
  background: #6c757d;
}

.btn-cancelar:hover {
  background: #5a6268;
}

/* Tabla historial */
.tabla-wrapper {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  text-align: left;
  padding: 12px 15px;
  border-bottom: 1px solid #eee;
}

th {
  background: #f8f9fa;
  font-size: 13px;
  color: #555;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.vacio {
  text-align: center;
  color: #999;
  padding: 20px;
}

@media (max-width: 600px) {
  .cabecera {
    flex-direction: column;
    text-align: center;
  }

  .info-usuario h2 {
    text-align: center;
  }

  .form-grid {
    grid-template-columns: 1fr;
  }

  .stats-grid {
    grid-template-columns: 1fr;
  }
}
</style>