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

}