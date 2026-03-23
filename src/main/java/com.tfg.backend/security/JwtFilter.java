package com.tfg.backend.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.
        UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.
        SecurityContextHolder;

import org.springframework.security.core.authority.
        SimpleGrantedAuthority;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.
        OncePerRequestFilter;

import com.tfg.backend.model.Usuario;
import com.tfg.backend.repository.UsuarioRepository;

import java.io.IOException;
import java.util.*;

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

            throws ServletException,IOException{

        String header=

                request.getHeader("Authorization");

        if(header!=null && header.startsWith("Bearer ")){

            String token=

                    header.replace("Bearer ","");

            String email=

                    jwtService.extraerEmail(token);

            Optional<Usuario> usuario=

                    repo.findByEmail(email);

            if(usuario.isPresent()){

                List<SimpleGrantedAuthority>

                        auth=

                        List.of(

                                new SimpleGrantedAuthority(

                                        usuario.get().getRol()

                                )

                        );

                UsernamePasswordAuthenticationToken

                        authentication=

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

        filterChain.doFilter(

                request,

                response

        );

    }

}