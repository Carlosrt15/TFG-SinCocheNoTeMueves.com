import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8090/api',
  headers: {
    'Content-Type': 'application/json'
  }
})

// Interceptor: añade el token JWT automáticamente a cada petición
api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = 'Bearer ' + token
  }
  return config
})

// Interceptor de respuesta: si recibe 401 (token caducado o invalido) limpia sesión
api.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('usuario')
      // No redirigimos automáticamente para no romper formularios públicos
    }
    return Promise.reject(error)
  }
)

export default api
