package com.atomika.gitByCity.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenManager { //класс отвечающий за генерацию токенов

    public static final long TOKEN_VALIDITY = 10 * 60 * 60; // переменная отвечающая за жизненный цикл токена
    // секретный ключ, лежащий в properties, который знаем только мы для подписи
    @Value("${token.signing.key}")
    private String jwtSecret;

    public String generateJwtToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts
                .builder()
                .claims().empty().add(claims)
                .and()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(getKey())
                .compact();
    }

    public Boolean validateJwtToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);

        final Claims claims = Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Boolean isTokenExpired = claims.getExpiration().before(new Date());
        return (username.equals(userDetails.getUsername())) && !isTokenExpired;
    }

    public String getUsernameFromToken(String token) {
        final Claims claims = Jwts
                .parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
