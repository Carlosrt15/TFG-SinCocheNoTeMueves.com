package com.tfg.backend.service;

import com.tfg.backend.model.Favorito;

import com.tfg.backend.repository.FavoritoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class FavoritoService {

    @Autowired

    FavoritoRepository repo;

    public List<Favorito> listar(){

        return repo.findAll();

    }

    public Favorito guardar(Favorito f){

        return repo.save(f);

    }

    public void eliminar(Long id){

        repo.deleteById(id);

    }

}