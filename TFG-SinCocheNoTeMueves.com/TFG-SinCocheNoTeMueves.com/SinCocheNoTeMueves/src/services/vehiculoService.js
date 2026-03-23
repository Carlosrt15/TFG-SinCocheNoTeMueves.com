import api from "./api"

export const getVehiculos=async()=>{

const res=
await api.get("/vehiculos")

return res.data

}

export const getVehiculo=async(id)=>{

const res=
await api.get(

"/vehiculos/"+id

)

return res.data

}

export const crearVehiculo=
async(data)=>{

return await api.post(

"/vehiculos",

data

)

}

export const eliminarVehiculo=
async(id)=>{

return await api.delete(

"/vehiculos/"+id

)

}

export const actualizarVehiculo=
async(id,data)=>{

return await api.put(

"/vehiculos/"+id,

data

)

}