package com.tfg.backend.config;

import com.tfg.backend.security.JwtFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

        http
                // CSRF: deshabilitado para API REST + permitir formulario login de la consola H2
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                        .disable()
                )
                // Permitir que la consola de H2 se muestre en un <iframe> (la consola
                // se carga dentro de un frame de su propia pagina, sin esto Spring
                // Security pone X-Frame-Options: DENY y la consola sale en blanco).
                .headers(h -> h.frameOptions(f -> f.sameOrigin()))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints publicos
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/usuarios/registro",
                                "/api/contacto"
                        ).permitAll()
                        // Consola H2 (solo desarrollo)
                        .requestMatchers("/h2-console/**").permitAll()
                        // Listado y detalle de vehiculos publicos (GET)
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/vehiculos/**").permitAll()
                        // Solo admin
                        .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                        // Resto requiere autenticacion
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
