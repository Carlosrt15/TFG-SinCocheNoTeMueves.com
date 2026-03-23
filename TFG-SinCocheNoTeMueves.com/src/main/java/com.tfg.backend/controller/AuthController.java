package com.tfg.backend.controller;

import com.tfg.backend.model.Usuario;
import com.tfg.backend.repository.UsuarioRepository;
import com.tfg.backend.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController

@RequestMapping("/api/auth")

@CrossOrigin(origins="http://localhost:5173")

public class AuthController {

    @Autowired
    UsuarioRepository repo;

    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario request){

        Optional<Usuario> usuario=
                repo.findByEmail(request.getEmail());

        if(usuario.isPresent()){

            if(usuario.get().getPassword()
                    .equals(request.getPassword())){

                String token = jwtService.generarToken(
                        usuario.get().getEmail());

                Map<String, Object> respuesta = new HashMap<>();
                respuesta.put("token", token);
                respuesta.put("usuario", usuario.get());

                return ResponseEntity.ok(respuesta);

            }

        }

        return ResponseEntity.status(401).body("Credenciales incorrectas");

    }

}