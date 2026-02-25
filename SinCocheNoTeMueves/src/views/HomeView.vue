<template>
  <div class="home">

    <!-- HERO -->
    <section class="hero">
      <div class="hero-content">
        <h1>Sin Coche No Te Mueves</h1>
        <p>Compra, vende o alquila vehículos de forma rápida y segura.</p>
        
        <p>No te quedes con un SsangYong… cuando puedes conducir tus sueños.</p>
        <router-link to="/vehiculos">
          <button>Ver Vehículos</button>
        </router-link>
      </div>
    </section>

    <!-- SECCIÓN INFORMACIÓN -->
    <section class="info container">
      <div class="card">
        <h3>Compra</h3>
        <p>Encuentra el coche perfecto entre cientos de vehículos disponibles.</p>
      </div>

      <div class="card">
        <h3>Vende</h3>
        <p>Publica tu vehículo fácilmente y gestiona tus ventas.</p>
      </div>

      <div class="card">
        <h3>Alquila</h3>
        <p>Alquila coches por días o semanas de forma segura.</p>
      </div>
    </section>

  </div>
</template>

<script setup>
</script>

<style scoped>
.hero {
  height: 80vh;
  background: linear-gradient(rgba(0,0,0,0.6), rgba(0,0,0,0.6)),
              url('https://images.unsplash.com/photo-1503376780353-7e6692767b70');
  background-size: cover;
  background-position: center;
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  text-align: center;
}

.hero-content h1 {
  font-size: 48px;
  margin-bottom: 20px;
}

.hero-content p {
  font-size: 20px;
  margin-bottom: 30px;
}

button {
  background: #0077ff;
  border: none;
  padding: 12px 25px;
  border-radius: 8px;
  color: white;
  font-size: 16px;
  cursor: pointer;
  transition: 0.3s;
}

button:hover {
  background: #005edb;
}

.container {
  max-width: 1100px;
  margin: auto;
  padding: 60px 20px;
}

.info {
  display: flex;
  gap: 30px;
  justify-content: center;
  flex-wrap: wrap;
}

.card {
  background: white;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0,0,0,0.1);
  width: 280px;
  text-align: center;
  transition: 0.3s;
}

.card:hover {
  transform: translateY(-5px);
}

.card h3 {
  margin-bottom: 15px;
}
</style>