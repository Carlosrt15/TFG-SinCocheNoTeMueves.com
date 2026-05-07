package com.tfg.backend.service;

import com.tfg.backend.model.Usuario;
import com.tfg.backend.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    UsuarioRepository repo;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Verifica credenciales contra el hash BCrypt almacenado.
     * Retorna el Usuario si coinciden, null si no.
     */
    public Usuario login(String email, String password){
        Optional<Usuario> usuario = repo.findByEmail(email);
        if (usuario.isPresent()
                && passwordEncoder.matches(password, usuario.get().getPassword())) {
            return usuario.get();
        }
        return null;
    }
}
