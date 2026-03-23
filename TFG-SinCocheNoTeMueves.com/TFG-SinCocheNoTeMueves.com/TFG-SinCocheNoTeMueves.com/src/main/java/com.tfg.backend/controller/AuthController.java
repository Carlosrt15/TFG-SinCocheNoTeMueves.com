package com.tfg.backend.controller;

import com.tfg.backend.model.Usuario;

import com.tfg.backend.repository.UsuarioRepository;

import com.tfg.backend.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController

@RequestMapping("/api/auth")

@CrossOrigin(origins="http://localhost:5173")

public class AuthController {

    @Autowired

    UsuarioRepository repo;

    @Autowired

    JwtService jwtService;

    @PostMapping("/login")

    public Map<String,Object> login(

            @RequestBody Usuario request){

        Optional<Usuario>

                usuario=

                repo.findByEmail(

                        request.getEmail()

                );

        Map<String,Object>

                res=

                new HashMap<>();

        if(usuario.isPresent()){

            if(usuario.get()

                    .getPassword()

                    .equals(

                            request.getPassword()

                    )){

                String token=

                        jwtService.generarToken(

                                usuario.get()

                                        .getEmail()

                        );

                res.put(

                        "token",

                        token

                );

                res.put(

                        "usuario",

                        usuario.get()

                );

                return res;

            }

        }

        res.put(

                "error",

                "Credenciales incorrectas"

        );

        return res;

    }

}