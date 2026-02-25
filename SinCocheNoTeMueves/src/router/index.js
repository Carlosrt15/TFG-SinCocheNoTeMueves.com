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

const routes = [
  { path: '/', component: Home },
  { path: '/login', component: Login },
  { path: '/registro', component: Registro },
  { path: '/vehiculos', component: Vehiculos },
  { path: '/vehiculos/:id', component: VehiculoDetalle },
  { path: '/favoritos', component: Favoritos },
  { path: '/perfil', component: Perfil },
  { path: '/contacto', component: Contacto },
  { path: '/chat', component: Chat },
  { path: '/admin', component: Admin },
  { path: '/publicar', component: PublicarVehiculo },
  { path: '/editar/:id', component: EditarVehiculo },
  { path: '/comprar/:id', component: ConfirmarCompra }
]






  

export default createRouter({
  history: createWebHistory(),
  routes
})