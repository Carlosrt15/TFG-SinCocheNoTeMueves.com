<template>
  <section class="terms-page section section--sm">
    <div class="container">
      <div class="terms-card card-base">
        <h1 class="terms-title">Terminos de uso y servicio</h1>
        <p class="terms-updated">Ultima actualizacion: 24/03/2026</p>

        <p>
          Al acceder y utilizar SinCocheNoTeMueves.com aceptas estos terminos. Si no estas de acuerdo,
          debes dejar de usar la plataforma.
        </p>

        <h2>1. Objeto del servicio</h2>
        <p>
          SinCocheNoTeMueves.com es una plataforma para publicar, buscar y gestionar anuncios de vehiculos.
          No somos parte directa de la compraventa entre usuarios.
        </p>

        <h2>2. Registro y cuenta</h2>
        <ul>
          <li>Debes proporcionar datos reales y actualizados durante el registro.</li>
          <li>Eres responsable de la confidencialidad de tu cuenta y contrasena.</li>
          <li>No esta permitido ceder o compartir la cuenta con terceros sin autorizacion.</li>
        </ul>

        <h2>3. Uso permitido</h2>
        <ul>
          <li>Publicar contenido legal, veraz y relacionado con la finalidad de la plataforma.</li>
          <li>Respetar al resto de usuarios y no realizar actividades fraudulentas.</li>
          <li>No intentar vulnerar la seguridad, disponibilidad o funcionamiento del servicio.</li>
        </ul>

        <h2>4. Publicaciones de vehiculos</h2>
        <p>
          Cada usuario es responsable del contenido que publica (descripcion, imagenes, precio y estado del
          vehiculo). Nos reservamos el derecho de moderar o retirar anuncios que incumplan estas normas.
        </p>

        <h2>5. Limitacion de responsabilidad</h2>
        <p>
          SinCocheNoTeMueves.com no garantiza acuerdos entre usuarios ni responde por danos derivados de
          negociaciones o transacciones realizadas fuera de la plataforma.
        </p>

        <h2>6. Suspension o cancelacion de cuenta</h2>
        <p>
          Podemos suspender o cancelar cuentas que incumplan estos terminos, usen la plataforma con fines
          ilicitos o perjudiquen a otros usuarios.
        </p>

        <h2>7. Propiedad intelectual</h2>
        <p>
          El diseno, marca y elementos propios de la plataforma pertenecen a sus titulares. No esta permitida
          su reproduccion sin autorizacion.
        </p>

        <h2>8. Cambios en los terminos</h2>
        <p>
          Estos terminos pueden actualizarse para adaptarse a cambios legales o funcionales. El uso continuo
          de la plataforma implica la aceptacion de las actualizaciones.
        </p>

        <h2>9. Contacto</h2>
        <p>
          Para dudas relacionadas con estos terminos, utiliza el formulario disponible en la seccion de
          contacto de la plataforma.
        </p>
      </div>
    </div>
  </section>
</template>

<style scoped>
.terms-page {
  background: linear-gradient(180deg, var(--bg-alt) 0%, var(--bg) 100%);
}

.terms-card {
  max-width: 900px;
  margin: 0 auto;
  padding: 34px;
}

.terms-title {
  font-size: clamp(1.8rem, 2.8vw, 2.4rem);
  margin-bottom: 8px;
}

.terms-updated {
  color: var(--text-muted);
  font-size: 0.9rem;
  margin-bottom: 22px;
}

.terms-card h2 {
  margin-top: 24px;
  margin-bottom: 8px;
  font-size: 1.2rem;
}

.terms-card p,
.terms-card li {
  color: var(--navy-mid);
  line-height: 1.75;
}

.terms-card ul {
  margin: 8px 0 0 20px;
}

@media (max-width: 768px) {
  .terms-card {
    padding: 24px;
  }
}
</style>