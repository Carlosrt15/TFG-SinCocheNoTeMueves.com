package com.tfg.backend.controller;

import com.tfg.backend.model.Vehiculo;
import com.tfg.backend.repository.VehiculoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    VehiculoRepository repo;

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

    @PostMapping
    public Vehiculo crear(@RequestBody Vehiculo v){
        return repo.save(v);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
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
