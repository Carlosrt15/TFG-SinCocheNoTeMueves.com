package com.tfg.backend.controller;

import com.tfg.backend.model.Usuario;
import com.tfg.backend.model.Vehiculo;
import com.tfg.backend.repository.UsuarioRepository;
import com.tfg.backend.repository.VehiculoRepository;
import com.tfg.backend.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    VehiculoRepository repo;

    @Autowired
    UsuarioRepository usuarioRepo;

    @Autowired
    JwtService jwtService;

    /** Listado público de todos los vehículos */
    @GetMapping
    public List<Vehiculo> listar() {
        return repo.findAll();
    }

    /** Detalle público de un vehículo concreto */
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> buscar(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crear un vehículo. Requiere usuario autenticado: el propietario se
     * extrae automáticamente del JWT enviado en el header Authorization.
     */
    @PostMapping
    public ResponseEntity<Vehiculo> crear(
            @RequestBody Vehiculo v,
            @RequestHeader("Authorization") String token) {

        try {
            // Quitar prefijo "Bearer " si viene incluido
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String email = jwtService.extraerEmail(token);
            Usuario propietario = usuarioRepo.findByEmail(email).orElseThrow();
            v.setPropietario(propietario);
            return ResponseEntity.ok(repo.save(v));
        } catch (Exception ex) {
            return ResponseEntity.status(401).build();
        }
    }

    /** Actualizar un vehículo existente */
    @PutMapping("/{id}")
    public Vehiculo actualizar(@PathVariable Long id, @RequestBody Vehiculo v) {
        v.setId(id);
        return repo.save(v);
    }

    /** Eliminar un vehículo */
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
