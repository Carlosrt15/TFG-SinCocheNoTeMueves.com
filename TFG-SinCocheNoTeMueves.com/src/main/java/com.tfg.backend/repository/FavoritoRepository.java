package com.tfg.backend.repository;

import com.tfg.backend.model.Favorito;

import com.tfg.backend.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritoRepository
        extends JpaRepository<Favorito,Long>{

    List<Favorito> findByUsuario(Usuario usuario);

}