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

    private String mensaje;

    private LocalDateTime fecha;

    public Contacto(){

        fecha=LocalDateTime.now();

    }

}