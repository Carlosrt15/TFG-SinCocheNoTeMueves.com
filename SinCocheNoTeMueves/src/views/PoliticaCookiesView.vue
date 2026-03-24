<template>
  <section class="cookies-page section section--sm">
    <div class="container">
      <div class="cookies-card card-base">
        <h1 class="cookies-title">Politica de cookies</h1>
        <p class="cookies-updated">Ultima actualizacion: 24/03/2026</p>

        <p>
          Esta politica explica que son las cookies, que tipos utilizamos en SinCocheNoTeMueves.com y como
          puedes gestionarlas.
        </p>

        <h2>1. Que son las cookies</h2>
        <p>
          Las cookies son pequenos archivos que se almacenan en tu dispositivo al visitar una web. Sirven para
          recordar informacion sobre tu sesion y mejorar tu experiencia.
        </p>

        <h2>2. Tipos de cookies que podemos usar</h2>
        <ul>
          <li>Cookies tecnicas: necesarias para el funcionamiento basico de la plataforma.</li>
          <li>Cookies de preferencias: guardan ajustes como idioma o configuracion visual.</li>
          <li>Cookies de analitica: ayudan a conocer el uso del sitio para mejorar el servicio.</li>
        </ul>

        <h2>3. Finalidad</h2>
        <p>
          Utilizamos cookies para mantener sesiones activas, mejorar rendimiento, reforzar seguridad y analizar
          de forma agregada el comportamiento de navegacion.
        </p>

        <h2>4. Gestion de cookies</h2>
        <p>
          Puedes aceptar, bloquear o eliminar cookies desde la configuracion de tu navegador. Ten en cuenta que
          desactivar cookies tecnicas puede afectar al funcionamiento correcto de algunas funciones.
        </p>

        <h2>5. Cookies de terceros</h2>
        <p>
          Algunos servicios integrados pueden instalar cookies de terceros. Estas cookies se rigen por sus
          propias politicas de privacidad y cookies.
        </p>

        <h2>6. Cambios en esta politica</h2>
        <p>
          Esta politica puede actualizarse por cambios legales o tecnicos. Te recomendamos revisarla
          periodicamente.
        </p>
      </div>
    </div>
  </section>
</template>

<style scoped>
.cookies-page {
  background: linear-gradient(180deg, var(--bg-alt) 0%, var(--bg) 100%);
}

.cookies-card {
  max-width: 900px;
  margin: 0 auto;
  padding: 34px;
}

.cookies-title {
  font-size: clamp(1.8rem, 2.8vw, 2.4rem);
  margin-bottom: 8px;
}

.cookies-updated {
  color: var(--text-muted);
  font-size: 0.9rem;
  margin-bottom: 22px;
}

.cookies-card h2 {
  margin-top: 24px;
  margin-bottom: 8px;
  font-size: 1.2rem;
}

.cookies-card p,
.cookies-card li {
  color: var(--navy-mid);
  line-height: 1.75;
}

.cookies-card ul {
  margin: 8px 0 0 20px;
}

@media (max-width: 768px) {
  .cookies-card {
    padding: 24px;
  }
}
</style>