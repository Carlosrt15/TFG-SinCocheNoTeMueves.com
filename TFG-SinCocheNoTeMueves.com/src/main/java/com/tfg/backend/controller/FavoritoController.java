package com.tfg.backend.controller;

import com.tfg.backend.model.Favorito;
import com.tfg.backend.model.Usuario;
import com.tfg.backend.model.Vehiculo;

import com.tfg.backend.repository.FavoritoRepository;
import com.tfg.backend.repository.UsuarioRepository;
import com.tfg.backend.repository.VehiculoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    @Autowired
    FavoritoRepository repo;

    @Autowired
    VehiculoRepository vehiculoRepo;

    @Autowired
    UsuarioRepository usuarioRepo;

    /* FAVORITOS DEL USUARIO AUTENTICADO */
    @GetMapping
    public ResponseEntity<List<Favorito>> misFavoritos(Authentication auth){
        if (auth == null) return ResponseEntity.status(401).build();

        Usuario usuario = usuarioRepo.findByEmail(auth.getName()).orElse(null);
        if (usuario == null) return ResponseEntity.status(404).build();

        return ResponseEntity.ok(repo.findByUsuarioId(usuario.getId()));
    }

    /* ANADIR FAVORITO */
    @PostMapping("/{vehiculoId}")
    public ResponseEntity<Favorito> add(@PathVariable Long vehiculoId, Authentication auth){
        if (auth == null) return ResponseEntity.status(401).build();

        Usuario usuario = usuarioRepo.findByEmail(auth.getName()).orElse(null);
        Vehiculo vehiculo = vehiculoRepo.findById(vehiculoId).orElse(null);

        if (usuario == null || vehiculo == null) {
            return ResponseEntity.notFound().build();
        }

        // Evitar duplicados
        if (repo.existsByUsuarioIdAndVehiculoId(usuario.getId(), vehiculoId)) {
            return ResponseEntity.status(409).build();
        }

        Favorito f = new Favorito();
        f.setUsuario(usuario);
        f.setVehiculo(vehiculo);
        return ResponseEntity.ok(repo.save(f));
    }

    /* ELIMINAR */
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        repo.deleteById(id);
    }
}
