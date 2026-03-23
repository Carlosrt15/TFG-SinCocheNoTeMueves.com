package com.tfg.backend.repository;

import com.tfg.backend.model.Vehiculo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculoRepository
        extends JpaRepository<Vehiculo,Long>{

    List<Vehiculo> findByDisponible(boolean disponible);

}