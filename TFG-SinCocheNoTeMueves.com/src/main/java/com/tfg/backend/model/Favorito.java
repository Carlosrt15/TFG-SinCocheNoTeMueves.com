package com.tfg.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name="favoritos")
public class Favorito {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name="vehiculo_id")
    private Vehiculo vehiculo;

    public Favorito(){}

    public Long getId(){ return id; }
    public void setId(Long id){ this.id=id; }

    public Usuario getUsuario(){ return usuario; }
    public void setUsuario(Usuario usuario){ this.usuario=usuario; }

    public Vehiculo getVehiculo(){ return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo){ this.vehiculo=vehiculo; }
}
