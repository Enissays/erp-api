package com.est.erp_api.services;

import com.est.erp_api.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Service
public class JWTService {
    private final SecretKey key;
    private final JwtParser parser;

    public JWTService() {
        this.key = Keys.hmacShaKeyFor("1234567891011121314151617456465465456456465456465465465456".getBytes());
        this.parser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String generate(String id) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(id)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                .signWith(key);

        return builder.compact();
    }

    public String parse(String token) {
        Claims claims = parser.parseClaimsJws(token).getBody();
        System.out.println("Claims: " + claims); // Log the claims to check their contents
        return claims.getSubject();
    }

    public boolean validate(String token, User user) {
        Claims claims = parser.parseClaimsJws(token).getBody();
        System.out.println("Claims: " + claims); // Log the claims to check their contents
        if (claims.getExpiration().before(Date.from(Instant.now()))) return false;
        return claims.getSubject().equals(user.getUser_id());
    }
}
