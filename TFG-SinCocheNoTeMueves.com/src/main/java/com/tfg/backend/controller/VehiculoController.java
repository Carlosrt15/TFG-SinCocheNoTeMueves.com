package com.tfg.backend.controller;

import com.tfg.backend.model.Usuario;
import com.tfg.backend.model.Vehiculo;

import com.tfg.backend.repository.UsuarioRepository;
import com.tfg.backend.repository.VehiculoRepository;

import com.tfg.backend.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    VehiculoRepository repo;

<<<<<<< HEAD:TFG-SinCocheNoTeMueves.com/src/main/java/com/tfg/backend/controller/VehiculoController.java
=======
    @Autowired
    UsuarioRepository usuarioRepo;

    @Autowired
    JwtService jwtService;


>>>>>>> 7340b0a797af59b36fbda7a40179ce88e6869aa2:TFG-SinCocheNoTeMueves.com/src/main/java/com.tfg.backend/controller/VehiculoController.java
    @GetMapping
    public List<Vehiculo> listar(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> buscar(@PathVariable Long id){
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

<<<<<<< HEAD:TFG-SinCocheNoTeMueves.com/src/main/java/com/tfg/backend/controller/VehiculoController.java
    @PostMapping
    public Vehiculo crear(@RequestBody Vehiculo v){
=======

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

>>>>>>> 7340b0a797af59b36fbda7a40179ce88e6869aa2:TFG-SinCocheNoTeMueves.com/src/main/java/com.tfg.backend/controller/VehiculoController.java
        return repo.save(v);
    }

<<<<<<< HEAD:TFG-SinCocheNoTeMueves.com/src/main/java/com/tfg/backend/controller/VehiculoController.java
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
=======

    @DeleteMapping("/{id}")
    public void eliminar(

            @PathVariable Long id){

>>>>>>> 7340b0a797af59b36fbda7a40179ce88e6869aa2:TFG-SinCocheNoTeMueves.com/src/main/java/com.tfg.backend/controller/VehiculoController.java
        repo.deleteById(id);
    }

<<<<<<< HEAD:TFG-SinCocheNoTeMueves.com/src/main/java/com/tfg/backend/controller/VehiculoController.java
=======

>>>>>>> 7340b0a797af59b36fbda7a40179ce88e6869aa2:TFG-SinCocheNoTeMueves.com/src/main/java/com.tfg.backend/controller/VehiculoController.java
    @PutMapping("/{id}")
    public Vehiculo actualizar(
            @PathVariable Long id,
            @RequestBody Vehiculo v){
        v.setId(id);
        return repo.save(v);
    }
}
