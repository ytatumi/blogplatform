package com.example.blogplatform.config;

import com.example.blogplatform.model.entity.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtAuthUtil {

    private final String jwtSecret = "supersecretkey1212121233333zxzzxzyyyy1212121";


    public String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role", List.of(role))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();

 /*       return Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody(); */

    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }


     public List<String> extractRoles(String token) {
        return extractAllClaims(token).get("role", List.class);

    }
}
