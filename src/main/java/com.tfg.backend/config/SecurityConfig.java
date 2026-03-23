package com.tfg.backend.config;

import com.tfg.backend.security.JwtFilter;

import org.springframework.context.annotation.*;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.
        UsernamePasswordAuthenticationFilter;

@Configuration

public class SecurityConfig {

    @Bean

    SecurityFilterChain filterChain(

            HttpSecurity http,

            JwtFilter jwtFilter)

            throws Exception{

        http

                .csrf(csrf->csrf.disable())

                .authorizeHttpRequests(auth->auth

                        .requestMatchers(

                                "/api/auth/**",

                                "/api/usuarios/registro",

                                "/api/vehiculos",

                                "/api/contacto"

                        ).permitAll()

                        .requestMatchers("/api/admin/**")

                        .hasAuthority("ADMIN")

                        .anyRequest()

                        .authenticated()

                )

                .addFilterBefore(

                        jwtFilter,

                        UsernamePasswordAuthenticationFilter.class

                );

        return http.build();

    }

}