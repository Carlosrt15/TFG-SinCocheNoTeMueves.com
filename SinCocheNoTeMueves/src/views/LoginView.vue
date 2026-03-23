<template>
  <div class="login-wrapper">
    <div class="login-card">

      <h2>Iniciar Sesión</h2>

      <div v-if="mensajeError" class="mensaje error">
        {{ mensajeError }}
      </div>

      <form @submit.prevent="login">
        <input v-model="email" type="email" placeholder="Correo electrónico" required />

        <input
          v-model="password"
          type="password"
          placeholder="Contraseña"
          required
        />

        <button type="submit" :disabled="cargando">
          {{ cargando ? "Entrando..." : "Entrar" }}
        </button>
      </form>

      <p class="registro-link">
        ¿No tienes cuenta?
        <router-link to="/registro">Regístrate</router-link>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

import { useRouter } from "vue-router";

import api from "../services/api";

const router = useRouter();

const email = ref("");
const password = ref("");
const mensajeError = ref("");
const cargando = ref(false);

const login = async () => {
  mensajeError.value = "";

  try {
    cargando.value = true;

    const res = await api.post(
      "/auth/login",
      {
        email: email.value,
        password: password.value,
      },
    );

    localStorage.setItem("token", res.data.token);
    localStorage.setItem("usuario", JSON.stringify(res.data.usuario));

    router.push("/vehiculos");
  } catch {
    mensajeError.value = "Credenciales incorrectas";
  } finally {
    cargando.value = false;
  }
};
</script>

<style scoped>
.login-wrapper {
  min-height: 80vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #f0f4ff, #e6f0ff);
  padding: 20px;
}

.login-card {
  background: white;
  padding: 40px;
  border-radius: 16px;
  width: 100%;
  max-width: 420px;
  box-shadow: 0 15px 40px rgba(0,0,0,0.1);
}

h2 {
  text-align: center;
  margin-bottom: 25px;
  color: #1a1a2e;
  font-size: 1.8rem;
}

form {
  display: flex;
  flex-direction: column;
}

input {
  width: 100%;
  padding: 12px;
  margin-bottom: 15px;
  border-radius: 8px;
  border: 1px solid #ccc;
  font-size: 0.95rem;
  box-sizing: border-box;
  transition: 0.3s;
}

input:focus {
  border-color: #0077ff;
  outline: none;
  box-shadow: 0 0 0 3px rgba(0,119,255,0.15);
}

button {
  width: 100%;
  padding: 12px;
  background: #0077ff;
  border: 1px solid #0077ff;
  color: white;
  border-radius: 8px;
  cursor: pointer;
  font-weight: bold;
  font-size: 1rem;
  box-sizing: border-box;
  transition: 0.3s;
}

button:hover {
  background: #005edb;
  border-color: #005edb;
}

button:disabled {
  background: #a0a0a0;
  border-color: #a0a0a0;
  cursor: not-allowed;
}

.mensaje {
  padding: 10px;
  margin-bottom: 15px;
  border-radius: 8px;
  text-align: center;
  font-size: 0.9rem;
}

.error {
  background: #f8d7da;
  color: #721c24;
}

.registro-link {
  text-align: center;
  margin-top: 20px;
  color: #666;
  font-size: 0.9rem;
}

.registro-link a {
  color: #0077ff;
  font-weight: bold;
}

.registro-link a:hover {
  text-decoration: underline;
}

@media (max-width: 600px) {
  .login-card {
    padding: 25px;
  }
}
</style>
