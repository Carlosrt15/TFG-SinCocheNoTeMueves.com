package com.tfg.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name="vehiculos")

public class Vehiculo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)

    private Long id;

    private String marca;

    private String modelo;

    private String estado;

    private double precio;

    private boolean disponible=true;

    @ManyToOne

    @JoinColumn(name="propietario_id")

    private Usuario propietario;

    public Vehiculo(){}

    public Long getId(){
        return id;
    }

    public String getMarca(){
        return marca;
    }

    public void setMarca(String marca){
        this.marca=marca;
    }

    public String getModelo(){
        return modelo;
    }

    public void setModelo(String modelo){
        this.modelo=modelo;
    }

    public String getEstado(){
        return estado;
    }

    public void setEstado(String estado){
        this.estado=estado;
    }

    public double getPrecio(){
        return precio;
    }

    public void setPrecio(double precio){
        this.precio=precio;
    }

    public boolean isDisponible(){
        return disponible;
    }

    public void setDisponible(boolean disponible){
        this.disponible=disponible;
    }

    public Usuario getPropietario(){
        return propietario;
    }

    public void setPropietario(Usuario propietario){
        this.propietario=propietario;
    }
    public void setId(Long id){
        this.id=id;
    }
}