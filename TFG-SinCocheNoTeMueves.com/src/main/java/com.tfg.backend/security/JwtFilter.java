package com.tfg.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import com.tfg.backend.model.Usuario;
import com.tfg.backend.repository.UsuarioRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    UsuarioRepository repo;

    @Override

    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain)

            throws ServletException, IOException {

        String header =
                request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")){

            try{

                String token =
                        header.replace("Bearer ","");

                String email =
                        jwtService.extraerEmail(token);

                Optional<Usuario> usuario =
                        repo.findByEmail(email);

                if(usuario.isPresent()){

                    List<SimpleGrantedAuthority> auth =
                            List.of(
                                    new SimpleGrantedAuthority(
                                            usuario.get().getRol()
                                    )
                            );

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    auth
                            );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);

                }

            }
            catch(Exception e){

                System.out.println("JWT invalido");

            }

        }

        filterChain.doFilter(
                request,
                response
        );

    }

}