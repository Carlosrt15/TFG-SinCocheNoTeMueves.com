package com.tfg.backend.controller;

import com.tfg.backend.model.Usuario;
import com.tfg.backend.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UsuarioRepository repo;

    @GetMapping("/usuarios")
    public List<Usuario> usuarios(){
        // No exponer los hashes de password en la lista de admin
        List<Usuario> lista = repo.findAll();
        lista.forEach(u -> u.setPassword(null));
        return lista;
    }

    @DeleteMapping("/usuarios/{id}")
    public void eliminar(@PathVariable Long id){
        repo.deleteById(id);
    }
}
