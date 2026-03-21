package com.tfg.backend.service;

import com.tfg.backend.model.Usuario;

import com.tfg.backend.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class AuthService {

    @Autowired

    UsuarioRepository repo;

    public Usuario login(String email,String password){

        Optional<Usuario> usuario=
                repo.findByEmail(email);

        if(usuario.isPresent()){

            if(usuario.get().getPassword()
                    .equals(password)){

                return usuario.get();

            }

        }

        return null;

    }

}