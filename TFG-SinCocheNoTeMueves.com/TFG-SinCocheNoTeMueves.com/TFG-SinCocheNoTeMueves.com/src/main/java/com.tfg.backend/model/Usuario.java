package com.tfg.backend.model;

import jakarta.persistence.*;

@Entity

@Table(name="usuarios")

public class Usuario {

    @Id

    @GeneratedValue(strategy=GenerationType.IDENTITY)

    private Long id;

    private String nombre;

    private String apellidos;

    private String email;

    private String password;

    private String dni;

    private String telefono;

    private String rol="USER";

    public Usuario(){}

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public String getApellidos(){
        return apellidos;
    }

    public void setApellidos(String apellidos){
        this.apellidos=apellidos;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getDni(){
        return dni;
    }

    public void setDni(String dni){
        this.dni=dni;
    }

    public String getTelefono(){
        return telefono;
    }

    public void setTelefono(String telefono){
        this.telefono=telefono;
    }

    public String getRol(){
        return rol;
    }

    public void setRol(String rol){
        this.rol=rol;
    }

}