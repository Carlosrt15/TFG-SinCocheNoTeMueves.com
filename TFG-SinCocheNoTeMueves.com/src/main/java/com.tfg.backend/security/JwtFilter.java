package com.tfg.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    CustomUserDetailsService userService;

    @Override

    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain chain)

            throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");

        if(authHeader != null &&
                authHeader.startsWith("Bearer ")){

            String token =
                    authHeader.substring(7);

            if(jwtService.validarToken(token)){

                String email =
                        jwtService.extraerEmail(token);

                UserDetails user =
                        userService
                                .loadUserByUsername(email);

                UsernamePasswordAuthenticationToken auth =

                        new UsernamePasswordAuthenticationToken(

                                user,

                                null,

                                user.getAuthorities()

                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(auth);

            }

        }

        chain.doFilter(request,response);

    }

}