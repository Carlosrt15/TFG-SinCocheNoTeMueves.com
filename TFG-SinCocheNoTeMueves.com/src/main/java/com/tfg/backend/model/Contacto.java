package com.tfg.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="contacto")
public class Contacto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String apellidos;

    private String correo;

    private String asunto;

    @Column(length = 2000)
    private String mensaje;

    private LocalDateTime fecha;

    public Contacto(){
        fecha=LocalDateTime.now();
    }

    public Long getId(){ return id; }
    public void setId(Long id){ this.id=id; }

    public String getNombre(){ return nombre; }
    public void setNombre(String nombre){ this.nombre=nombre; }

    public String getApellidos(){ return apellidos; }
    public void setApellidos(String apellidos){ this.apellidos=apellidos; }

    public String getCorreo(){ return correo; }
    public void setCorreo(String correo){ this.correo=correo; }

    public String getAsunto(){ return asunto; }
    public void setAsunto(String asunto){ this.asunto=asunto; }

    public String getMensaje(){ return mensaje; }
    public void setMensaje(String mensaje){ this.mensaje=mensaje; }

    public LocalDateTime getFecha(){ return fecha; }
    public void setFecha(LocalDateTime fecha){ this.fecha=fecha; }
}
