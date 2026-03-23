package com.tfg.backend.repository;

import com.tfg.backend.model.Favorito;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritoRepository
        extends JpaRepository<Favorito,Long>{

    List<Favorito> findByUsuarioId(Long id);

    boolean existsByUsuarioIdAndVehiculoId(
            Long usuarioId,
            Long vehiculoId
    );

}