package com.tfg.backend.service;

import com.tfg.backend.model.Usuario;
import com.tfg.backend.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repo;

    public Usuario registrar(Usuario usuario){
        usuario.setRol("USER");
        return repo.save(usuario);
    }

    public List<Usuario> listar(){
        return repo.findAll();
    }

    public Optional<Usuario> buscar(Long id){
        return repo.findById(id);
    }

    public void eliminar(Long id){
        repo.deleteById(id);
    }
}
