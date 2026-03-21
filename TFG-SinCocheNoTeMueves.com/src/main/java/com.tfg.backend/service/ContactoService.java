package com.tfg.backend.service;

import com.tfg.backend.model.Contacto;

import com.tfg.backend.repository.ContactoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service

public class ContactoService {

    @Autowired

    ContactoRepository repo;

    public Contacto guardar(Contacto contacto){

        return repo.save(contacto);

    }

}