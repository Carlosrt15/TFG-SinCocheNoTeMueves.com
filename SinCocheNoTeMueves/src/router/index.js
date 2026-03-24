import { createRouter, createWebHistory } from 'vue-router'

import Home from '../views/HomeView.vue'
import Login from '../views/LoginView.vue'
import Registro from '../views/RegistroView.vue'
import Vehiculos from '../views/VehiculosView.vue'
import VehiculoDetalle from '../views/VehiculoDetalleView.vue'
import Favoritos from '../views/FavoritosView.vue'
import Perfil from '../views/PerfilView.vue'
import Contacto from '../views/ContactoView.vue'
import Chat from '../views/ChatView.vue'
import Admin from '../views/AdminView.vue'
import PublicarVehiculo from '../views/PublicarVehiculoView.vue'
import EditarVehiculo from '../views/EditarVehiculoView.vue'
import ConfirmarCompra from '../views/ConfirmarCompraView.vue'
import PoliticaPrivacidad from '../views/PoliticaPrivacidadView.vue'

const routes = [

  { path: '/', component: Home },

  { path: '/login', component: Login },

  { path: '/registro', component: Registro },

  { path: '/vehiculos', component: Vehiculos },

  { path: '/vehiculo/:id', component: VehiculoDetalle },

  { path: '/favoritos', component: Favoritos },

  { path: '/perfil', component: Perfil },

  { path: '/contacto', component: Contacto },

  { path: '/chat', component: Chat },

  { path: '/admin', component: Admin },

  { path: '/publicar', component: PublicarVehiculo },

  { path: '/editar/:id', component: EditarVehiculo },

  { path: '/comprar/:id', component: ConfirmarCompra },

  { path: '/politica-privacidad', component: PoliticaPrivacidad }

]

const router = createRouter({

  history: createWebHistory(),

  routes

})

/* PROTECCION JWT */

router.beforeEach((to, from, next) => {

  const token =
    localStorage.getItem("token")

  const rutasProtegidas = [

    "/perfil",

    "/favoritos",

    "/chat",

    "/publicar",

    "/admin"

  ]

  if (

    rutasProtegidas.includes(to.path)

    && !token

  ) {

    next("/login")

  }

  else {

    next()

  }

})

export default router