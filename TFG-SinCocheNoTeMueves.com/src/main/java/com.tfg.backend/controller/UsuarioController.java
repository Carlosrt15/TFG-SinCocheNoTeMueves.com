package com.tfg.backend.controller;

import com.tfg.backend.model.Usuario;
import com.tfg.backend.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/usuarios")

@CrossOrigin(origins="http://localhost:5173")

public class UsuarioController {

    @Autowired

    UsuarioRepository repo;

    @PostMapping("/registro")

    public Usuario registro(
            @RequestBody Usuario usuario){

        usuario.setRol("USER");

        return repo.save(usuario);

    }

    @GetMapping

    public List<Usuario> listar(){

        return repo.findAll();

    }

}