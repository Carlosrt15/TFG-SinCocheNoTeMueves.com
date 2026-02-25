<template>
  <footer class="footer">
    <div class="container footer-content">
      <div>
        <h3>SinCocheNoTeMueves</h3>
        <p>Plataforma de compra, venta y alquiler de vehículos.</p>
      </div>

      <div>
        <h4>Contacto</h4>
        <p>Email: info@sincochenotemueves.com</p>
        <p>Teléfono: 600 000 000</p>
        <p>Salamanca, España</p>
      </div>

      <div>
        <h4>Enlaces</h4>
        <router-link to="/">Inicio</router-link><br />
        <router-link to="/vehiculos">Vehículos</router-link><br />
        <router-link to="/contacto">Contacto</router-link>
      </div>
    </div>

    <div class="copy">
      © 2026 SinCocheNoTeMueves - Proyecto DAW
    </div>
  </footer>
</template>

<script setup>
</script>

<style scoped>
.footer {
  background: #1e293b;
  color: white;
  margin-top: 60px;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  padding: 40px 20px;
  flex-wrap: wrap;
  gap: 30px;
}

.footer h3, .footer h4 {
  margin-bottom: 10px;
}

.footer a {
  color: #00c3ff;
  text-decoration: none;
}

.copy {
  text-align: center;
  padding: 15px;
  background: #0f172a;
  font-size: 14px;
}
</style>