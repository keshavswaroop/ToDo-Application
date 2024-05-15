package com.todo.todobackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    //generate token

    public String getToken(Authentication authentication) {
        String username = authentication.getName();

        Date currentDate = new Date();

        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);

        //creating token
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(keys())
                .compact();

        return token;
    }

    private Key keys() {   //Key is from java.security class
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));

    }


    //get username from the token
    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(keys())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();

        return username;
    }

    //validates the jwt token
    public boolean validateToken(String token) {
        Jwts.parser()
                .setSigningKey(keys())
                .build()
                .parse(token);
        return true;
    }
}
