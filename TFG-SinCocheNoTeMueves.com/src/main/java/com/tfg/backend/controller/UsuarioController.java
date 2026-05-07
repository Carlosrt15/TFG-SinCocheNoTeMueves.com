package com.tfg.backend.controller;

import com.tfg.backend.model.Usuario;
import com.tfg.backend.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioRepository repo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario u){

        // Validacion basica: evitar email duplicado
        if (repo.findByEmail(u.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(409)
                    .body(Map.of("error", "Ya existe un usuario con ese email"));
        }

        // Hashear la contrasena antes de guardar
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        u.setRol("USER");

        Usuario guardado = repo.save(u);
        // No devolver el hash al cliente
        guardado.setPassword(null);
        return ResponseEntity.ok(guardado);
    }
}
