package com.tfg.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="chat")
public class Chat {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String sala;

    private String mensaje;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    public Chat(){
        fecha=LocalDateTime.now();
    }

    public Long getId(){ return id; }
    public void setId(Long id){ this.id=id; }

    public String getSala(){ return sala; }
    public void setSala(String sala){ this.sala=sala; }

    public String getMensaje(){ return mensaje; }
    public void setMensaje(String mensaje){ this.mensaje=mensaje; }

    public LocalDateTime getFecha(){ return fecha; }
    public void setFecha(LocalDateTime fecha){ this.fecha=fecha; }

    public Usuario getUsuario(){ return usuario; }
    public void setUsuario(Usuario usuario){ this.usuario=usuario; }
}
