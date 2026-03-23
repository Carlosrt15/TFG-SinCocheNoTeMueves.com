import axios from "axios"

const api = axios.create({

    baseURL:"http://localhost:8080/api",

    headers:{
        "Content-Type":"application/json"
    }

})

/*
 Añade automáticamente el JWT
 a todas las peticiones
*/
api.interceptors.request.use(

    config=>{

        const token=

            localStorage.getItem("token")

        if(token && token!="null"){

            config.headers.Authorization=

                "Bearer "+token

        }

        return config

    },

    error=>{

        return Promise.reject(error)

    }

)

export default api