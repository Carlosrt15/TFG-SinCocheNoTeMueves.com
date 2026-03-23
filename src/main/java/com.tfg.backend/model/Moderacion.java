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

}