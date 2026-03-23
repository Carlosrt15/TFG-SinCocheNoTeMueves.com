import { createRouter, createWebHistory }

  from 'vue-router'

import Home from '../views/HomeView.vue'
import Login from '../views/LoginView.vue'
import Registro from '../views/RegistroView.vue'
import Vehiculos from '../views/VehiculosView.vue'
import Perfil from '../views/PerfilView.vue'
import Admin from '../views/AdminView.vue'

const routes = [

  { path: '/', component: Home },

  { path: '/login', component: Login },

  { path: '/registro', component: Registro },

  { path: '/vehiculos', component: Vehiculos },

  { path: '/perfil', component: Perfil },

  { path: '/admin', component: Admin }

]

const router =

  createRouter({

    history:

      createWebHistory(),

    routes

  })

router.beforeEach((to) => {

  const token =

    localStorage.getItem("token")

  const user =

    JSON.parse(

      localStorage.getItem("usuario")

    )

  if (

    to.path == "/perfil"

    && !token

  ) {

    return "/login"

  }

  if (

    to.path == "/admin"

    && user?.rol != "ADMIN"

  ) {

    return "/"

  }

})

export default router