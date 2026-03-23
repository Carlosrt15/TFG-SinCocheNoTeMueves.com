package com.tfg.backend.service;

import com.tfg.backend.model.Vehiculo;
import com.tfg.backend.repository.VehiculoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class VehiculoService {

    @Autowired

    VehiculoRepository repo;

    public List<Vehiculo> listar(){

        return repo.findAll();

    }

    public Vehiculo crear(Vehiculo v){

        return repo.save(v);

    }

    public Optional<Vehiculo> buscar(Long id){

        return repo.findById(id);

    }

    public Vehiculo actualizar(Vehiculo v){

        return repo.save(v);

    }

    public void eliminar(Long id){

        repo.deleteById(id);

    }

}