package com.tfg.backend.controller;

import com.tfg.backend.model.Usuario;
import com.tfg.backend.model.Vehiculo;

import com.tfg.backend.repository.UsuarioRepository;
import com.tfg.backend.repository.VehiculoRepository;

import com.tfg.backend.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController

@RequestMapping("/api/vehiculos")

@CrossOrigin(origins="http://localhost:5173")

public class VehiculoController {

    @Autowired
    VehiculoRepository repo;

    @Autowired
    UsuarioRepository usuarioRepo;

    @Autowired
    JwtService jwtService;


    @GetMapping
    public List<Vehiculo> listar(){

        return repo.findAll();

    }


    @PostMapping
    public Vehiculo crear(

            @RequestBody Vehiculo v,

            @RequestHeader("Authorization") String token

    ){

        token=token.replace("Bearer ","");

        String email=

                jwtService.extraerEmail(token);

        Usuario usuario=

                usuarioRepo
                        .findByEmail(email)
                        .orElseThrow();

        v.setPropietario(usuario);

        return repo.save(v);

    }


    @DeleteMapping("/{id}")
    public void eliminar(

            @PathVariable Long id){

        repo.deleteById(id);

    }


    @PutMapping("/{id}")
    public Vehiculo actualizar(

            @PathVariable Long id,

            @RequestBody Vehiculo v){

        v.setId(id);

        return repo.save(v);

    }

}