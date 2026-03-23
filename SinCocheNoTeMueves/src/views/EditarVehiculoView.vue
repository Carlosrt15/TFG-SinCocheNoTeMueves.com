<template>
  <div>
    <div class="page-header">
      <div class="container page-header-content">
        <h1>✏️ Editar vehículo</h1>
        <p>Actualiza los datos de tu anuncio</p>
      </div>
    </div>

    <div class="container" style="max-width: 700px; padding: 40px 16px 80px">
      <div class="card-base editar-form-wrap">
        <div v-if="mensaje" class="alert" :class="exito ? 'alert-success' : 'alert-error'">
          {{ exito ? '✅' : '⚠️' }} {{ mensaje }}
        </div>

        <form @submit.prevent="guardar">
          <div class="form-row-2">
            <div class="form-group">
              <label class="form-label">Marca</label>
              <input v-model="vehiculo.marca" class="form-input" required />
            </div>
            <div class="form-group">
              <label class="form-label">Modelo</label>
              <input v-model="vehiculo.modelo" class="form-input" required />
            </div>
          </div>

          <div class="form-row-2">
            <div class="form-group">
              <label class="form-label">Estado</label>
              <select v-model="vehiculo.estado" class="form-input">
                <option value="Nuevo">Nuevo</option>
                <option value="Usado">Usado</option>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label">Precio (€)</label>
              <input v-model.number="vehiculo.precio" type="number" class="form-input" required min="0" />
            </div>
          </div>

          <div class="form-actions">
            <button type="submit" class="btn btn-primary">💾 Guardar cambios</button>
            <router-link to="/vehiculos" class="btn btn-outline">Cancelar</router-link>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { reactive, ref, onMounted } from 'vue'
import { getVehiculo, actualizarVehiculo } from '../services/vehiculoService'

const route = useRoute()
const vehiculo = reactive({ id: null, marca: '', modelo: '', estado: '', precio: 0 })
const mensaje = ref('')
const exito = ref(false)

onMounted(async () => {
  try {
    const data = await getVehiculo(route.params.id)
    Object.assign(vehiculo, data)
  } catch { mensaje.value = 'Error al cargar el vehículo' }
})

const guardar = async () => {
  try {
    await actualizarVehiculo(vehiculo.id, vehiculo)
    exito.value = true
    mensaje.value = 'Cambios guardados correctamente'
  } catch {
    exito.value = false
    mensaje.value = 'Error al guardar los cambios'
  }
}
</script>

<style scoped>
.editar-form-wrap { padding: 36px; }
.form-row-2 { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
.form-actions { display: flex; gap: 12px; margin-top: 8px; }

@media (max-width: 600px) {
  .form-row-2 { grid-template-columns: 1fr; gap: 0; }
  .form-actions { flex-direction: column; }
}
</style>
