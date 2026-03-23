package com.tfg.backend.controller;

import com.tfg.backend.model.Usuario;

import com.tfg.backend.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/admin")

@CrossOrigin(origins="http://localhost:5173")

public class AdminController {

    @Autowired

    UsuarioRepository repo;

    @GetMapping("/usuarios")

    public List<Usuario> usuarios(){

        return repo.findAll();

    }

    @DeleteMapping("/usuarios/{id}")

    public void eliminar(

            @PathVariable Long id){

        repo.deleteById(id);

    }

}