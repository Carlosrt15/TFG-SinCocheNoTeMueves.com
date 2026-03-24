<template>
  <section class="privacy-page section section--sm">
    <div class="container">
      <div class="privacy-card card-base">
        <h1 class="privacy-title">Politica de datos y privacidad</h1>
        <p class="privacy-updated">Ultima actualizacion: 24/03/2026</p>

        <p>
          En SinCocheNoTeMueves tratamos tus datos personales con responsabilidad y transparencia.
          Este apartado explica que informacion recogemos, para que la usamos y cuales son tus derechos.
        </p>

        <h2>1. Responsable del tratamiento</h2>
        <p>
          Responsable: SinCocheNoTeMueves.com<br />
          Contacto: usa el formulario de la seccion de contacto de la plataforma.
        </p>

        <h2>2. Datos que recopilamos</h2>
        <ul>
          <li>Datos de registro: nombre, apellidos, DNI, correo electronico, telefono y contrasena.</li>
          <li>Datos de uso: informacion relacionada con tus publicaciones, favoritos y mensajes.</li>
          <li>Datos tecnicos basicos: IP aproximada, navegador y fecha/hora de acceso para seguridad.</li>
        </ul>

        <h2>3. Finalidad del tratamiento</h2>
        <ul>
          <li>Crear y gestionar tu cuenta de usuario.</li>
          <li>Permitir la compra, venta y gestion de anuncios de vehiculos.</li>
          <li>Mejorar la seguridad de la plataforma y prevenir usos fraudulentos.</li>
          <li>Atender consultas y soporte cuando nos contactes.</li>
        </ul>

        <h2>4. Base legal</h2>
        <p>
          Tratamos tus datos porque son necesarios para ejecutar el servicio que solicitas al registrarte y,
          en su caso, por cumplimiento de obligaciones legales.
        </p>

        <h2>5. Conservacion de datos</h2>
        <p>
          Conservamos los datos mientras mantengas tu cuenta activa o durante el tiempo necesario para cumplir
          obligaciones legales y resolver posibles reclamaciones.
        </p>

        <h2>6. Cesion de datos a terceros</h2>
        <p>
          No vendemos tus datos personales. Solo compartimos datos cuando es necesario para el funcionamiento
          tecnico del servicio o por obligacion legal.
        </p>

        <h2>7. Tus derechos</h2>
        <p>Puedes solicitar en cualquier momento:</p>
        <ul>
          <li>Acceso a tus datos.</li>
          <li>Rectificacion de datos incorrectos.</li>
          <li>Supresion de tu informacion personal.</li>
          <li>Limitacion u oposicion al tratamiento cuando proceda.</li>
        </ul>

        <h2>8. Seguridad</h2>
        <p>
          Aplicamos medidas tecnicas y organizativas razonables para proteger la informacion frente a accesos
          no autorizados, perdida o alteracion.
        </p>

        <h2>9. Cambios en esta politica</h2>
        <p>
          Esta politica puede actualizarse para adaptarse a cambios legales o mejoras del servicio.
          Te recomendamos revisarla periodicamente.
        </p>
      </div>
    </div>
  </section>
</template>

<style scoped>
.privacy-page {
  background: linear-gradient(180deg, var(--bg-alt) 0%, var(--bg) 100%);
}

.privacy-card {
  max-width: 900px;
  margin: 0 auto;
  padding: 34px;
}

.privacy-title {
  font-size: clamp(1.8rem, 2.8vw, 2.4rem);
  margin-bottom: 8px;
}

.privacy-updated {
  color: var(--text-muted);
  font-size: 0.9rem;
  margin-bottom: 22px;
}

.privacy-card h2 {
  margin-top: 24px;
  margin-bottom: 8px;
  font-size: 1.2rem;
}

.privacy-card p,
.privacy-card li {
  color: var(--navy-mid);
  line-height: 1.75;
}

.privacy-card ul {
  margin: 8px 0 0 20px;
}

@media (max-width: 768px) {
  .privacy-card {
    padding: 24px;
  }
}
</style>