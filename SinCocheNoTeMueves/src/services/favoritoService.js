import api from "./api"

export const addFavorito=
async(vehiculoId)=>{

return await api.post(

"/favoritos/"+vehiculoId

)

}

export const getFavoritos=
async()=>{

const res=
await api.get(

"/favoritos"

)

return res.data

}