package com.tfg.backend.security;

import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import java.security.Key;

import java.util.Date;

@Service

public class JwtService {

    private final String SECRET=
            "sincochenotemuevestfg2025securityjwt";

    private Key key(){

        return Keys.hmacShaKeyFor(
                SECRET.getBytes()
        );

    }

    public String generarToken(String email){

        return Jwts.builder()

                .setSubject(email)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + 1000*60*60*10)
                )

                .signWith(key())

                .compact();

    }

    public String extraerEmail(String token){

        return Jwts.parserBuilder()

                .setSigningKey(key())

                .build()

                .parseClaimsJws(token)

                .getBody()

                .getSubject();

    }

    public boolean validarToken(String token){

        try{

            Jwts.parserBuilder()

                    .setSigningKey(key())

                    .build()

                    .parseClaimsJws(token);

            return true;

        }

        catch(Exception e){

            return false;

        }

    }

}