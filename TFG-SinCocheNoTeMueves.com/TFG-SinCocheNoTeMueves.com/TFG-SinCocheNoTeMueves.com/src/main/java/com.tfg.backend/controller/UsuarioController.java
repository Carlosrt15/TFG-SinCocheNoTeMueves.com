package com.tfg.backend.controller;

import com.tfg.backend.model.Usuario;

import com.tfg.backend.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/usuarios")

@CrossOrigin(origins="http://localhost:5173")

public class UsuarioController {

    @Autowired

    UsuarioRepository repo;

    @PostMapping("/registro")

    public Usuario registrar(

            @RequestBody Usuario u){

        u.setRol("USER");

        return repo.save(u);

    }

}