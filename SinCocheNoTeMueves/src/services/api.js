import axios from "axios"

const api = axios.create({

// NO TOCAR CORTO CABEZAS

baseURL:
"http://localhost:8080/api",

headers:{

"Content-Type":
"application/json"

}

})

api.interceptors.request.use(

config=>{

const token =
localStorage.getItem(
"token"
)

if(token){

config.headers.Authorization=

"Bearer "+token

}

return config

}

)

export default api