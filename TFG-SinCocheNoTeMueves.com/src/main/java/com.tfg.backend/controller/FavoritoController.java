package com.tfg.backend.controller;

import com.tfg.backend.model.Favorito;
import com.tfg.backend.model.Usuario;
import com.tfg.backend.model.Vehiculo;

import com.tfg.backend.repository.FavoritoRepository;
import com.tfg.backend.repository.UsuarioRepository;
import com.tfg.backend.repository.VehiculoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/favoritos")

@CrossOrigin(origins="http://localhost:5173")

public class FavoritoController {

    @Autowired

    FavoritoRepository repo;

    @Autowired

    VehiculoRepository vehiculoRepo;

    @Autowired

    UsuarioRepository usuarioRepo;


    /* FAVORITOS DEL USUARIO */

    @GetMapping

    public List<Favorito> misFavoritos(
            Authentication auth){

        Usuario usuario =

                usuarioRepo
                        .findByEmail(
                                auth.getName()
                        )
                        .orElse(null);

        return repo.findByUsuarioId(
                usuario.getId()
        );

    }


    /* AÑADIR FAVORITO */

    @PostMapping("/{vehiculoId}")

    public Favorito add(

            @PathVariable Long vehiculoId,

            Authentication auth){

        Usuario usuario =

                usuarioRepo
                        .findByEmail(
                                auth.getName()
                        )
                        .orElse(null);

        Vehiculo vehiculo =

                vehiculoRepo
                        .findById(vehiculoId)
                        .orElse(null);


        /* evitar duplicados */

        if(repo.existsByUsuarioIdAndVehiculoId(

                usuario.getId(),

                vehiculoId

        )){

            return null;

        }

        Favorito f =
                new Favorito();

        f.setUsuario(usuario);

        f.setVehiculo(vehiculo);

        return repo.save(f);

    }


    /* ELIMINAR */

    @DeleteMapping("/{id}")

    public void eliminar(

            @PathVariable Long id){

        repo.deleteById(id);

    }

}