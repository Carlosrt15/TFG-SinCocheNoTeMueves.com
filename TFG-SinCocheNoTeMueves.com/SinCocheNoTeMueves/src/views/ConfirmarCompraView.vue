<template>
  <div class="confirmar-page">
    <div class="container confirmar-container">

      <div v-if="!compraRealizada" class="card-base confirmar-card">
        <div class="confirmar-icon">🛒</div>
        <h1>Confirmar compra</h1>
        <p class="confirmar-desc">
          Estás a punto de completar la transacción. Una vez confirmada, recibirás un email de confirmación y el vendedor será notificado.
        </p>

        <div class="confirmar-garantias">
          <div class="garantia-item">
            <span>🔒</span>
            <span>Pago 100% seguro</span>
          </div>
          <div class="garantia-item">
            <span>↩️</span>
            <span>Garantía de devolución</span>
          </div>
          <div class="garantia-item">
            <span>📞</span>
            <span>Soporte inmediato</span>
          </div>
        </div>

        <div class="confirmar-actions">
          <button class="btn btn-primary btn-confirmar" @click="confirmar">
            ✅ Confirmar compra
          </button>
          <router-link to="/vehiculos" class="btn btn-outline">Cancelar</router-link>
        </div>
      </div>

      <div v-else class="card-base exito-card">
        <div class="exito-icon">🎉</div>
        <h1>¡Compra realizada!</h1>
        <p>Tu compra se ha procesado correctamente. Recibirás un email de confirmación en breve.</p>
        <div class="exito-actions">
          <router-link to="/perfil" class="btn btn-primary">Ver mis compras</router-link>
          <router-link to="/vehiculos" class="btn btn-outline">Seguir explorando</router-link>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const compraRealizada = ref(false)
const confirmar = () => { compraRealizada.value = true }
</script>

<style scoped>
.confirmar-page {
  min-height: calc(100vh - var(--navbar-h));
  display: flex;
  align-items: center;
  background: var(--bg);
  padding: 60px 0;
}

.confirmar-container { max-width: 560px; margin: 0 auto; padding: 0 16px; width: 100%; }

.confirmar-card, .exito-card {
  padding: 56px 48px;
  text-align: center;
}

.confirmar-icon, .exito-icon {
  font-size: 3.5rem;
  margin-bottom: 20px;
  display: block;
}

.confirmar-card h1, .exito-card h1 {
  font-size: 1.8rem;
  margin-bottom: 14px;
}

.confirmar-desc {
  color: var(--text-muted);
  line-height: 1.7;
  margin-bottom: 32px;
  font-size: 0.95rem;
}

.exito-card p {
  color: var(--text-muted);
  line-height: 1.65;
  margin-bottom: 32px;
}

.confirmar-garantias {
  display: flex;
  justify-content: center;
  gap: 28px;
  margin-bottom: 36px;
  flex-wrap: wrap;
}

.garantia-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 6px;
  font-size: 0.82rem;
  color: var(--text-muted);
}

.garantia-item span:first-child { font-size: 1.4rem; }

.confirmar-actions, .exito-actions {
  display: flex;
  gap: 12px;
  justify-content: center;
  flex-wrap: wrap;
}

.btn-confirmar { padding: 15px 36px; font-size: 1rem; }

@media (max-width: 560px) {
  .confirmar-card, .exito-card { padding: 36px 24px; }
  .confirmar-garantias { gap: 16px; }
}
</style>
