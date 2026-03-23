package com.tfg.backend.controller;

import com.tfg.backend.model.Favorito;
import com.tfg.backend.repository.FavoritoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/favoritos")

@CrossOrigin(origins="http://localhost:5173")

public class FavoritoController {

    @Autowired

    FavoritoRepository repo;

    @GetMapping

    public List<Favorito> listar(){

        return repo.findAll();

    }

    @PostMapping

    public Favorito guardar(
            @RequestBody Favorito f){

        return repo.save(f);

    }

    @DeleteMapping("/{id}")

    public void eliminar(
            @PathVariable Long id){

        repo.deleteById(id);

    }

}