package com.tfg.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private int rating;

    @Column(length = 1000)
    private String opinion;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    public Resena(){
        fecha = LocalDateTime.now();
    }

    public Long getId(){ return id; }
    public void setId(Long id){ this.id=id; }

    public int getRating(){ return rating; }
    public void setRating(int rating){ this.rating=rating; }

    public String getOpinion(){ return opinion; }
    public void setOpinion(String opinion){ this.opinion=opinion; }

    public LocalDateTime getFecha(){ return fecha; }
    public void setFecha(LocalDateTime fecha){ this.fecha=fecha; }

    public Usuario getUsuario(){ return usuario; }
    public void setUsuario(Usuario usuario){ this.usuario=usuario; }
}
