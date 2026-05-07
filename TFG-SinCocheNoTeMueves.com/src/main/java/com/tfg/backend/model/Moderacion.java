package com.tfg.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="moderacion")
public class Moderacion {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String accion;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name="admin_id")
    private Usuario admin;

    @ManyToOne
    @JoinColumn(name="usuario_afectado_id")
    private Usuario usuario;

    public Moderacion(){
        fecha=LocalDateTime.now();
    }

    public Long getId(){ return id; }
    public void setId(Long id){ this.id=id; }

    public String getAccion(){ return accion; }
    public void setAccion(String accion){ this.accion=accion; }

    public LocalDateTime getFecha(){ return fecha; }
    public void setFecha(LocalDateTime fecha){ this.fecha=fecha; }

    public Usuario getAdmin(){ return admin; }
    public void setAdmin(Usuario admin){ this.admin=admin; }

    public Usuario getUsuario(){ return usuario; }
    public void setUsuario(Usuario usuario){ this.usuario=usuario; }
}
