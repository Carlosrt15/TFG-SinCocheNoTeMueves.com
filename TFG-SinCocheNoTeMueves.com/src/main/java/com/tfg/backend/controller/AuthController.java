package com.tfg.backend.controller;

import com.tfg.backend.model.Usuario;
import com.tfg.backend.service.AuthService;
import com.tfg.backend.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody Usuario request){
        Usuario usuario = authService.login(request.getEmail(), request.getPassword());

        Map<String,Object> res = new HashMap<>();

        if (usuario != null) {
            String token = jwtService.generarToken(usuario.getEmail());
            // Nunca devolver el hash de contrasena al cliente
            usuario.setPassword(null);
            res.put("token", token);
            res.put("usuario", usuario);
            return ResponseEntity.ok(res);
        }

        res.put("error", "Credenciales incorrectas");
        return ResponseEntity.status(401).body(res);
    }
}
