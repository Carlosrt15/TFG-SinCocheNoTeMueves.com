package com.tfg.backend.controller;

import com.tfg.backend.model.Contacto;
import com.tfg.backend.repository.ContactoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacto")
public class ContactoController {

    @Autowired
    ContactoRepository repo;

    @PostMapping
    public Contacto guardar(@RequestBody Contacto contacto){
        return repo.save(contacto);
    }
}
