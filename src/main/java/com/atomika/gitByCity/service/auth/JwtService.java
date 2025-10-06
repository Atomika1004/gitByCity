package com.atomika.gitByCity.service.auth;

import com.atomika.gitByCity.dto.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.*;

@Component
public class JwtService {

    @Value("${token.expired.access}")
    public Long expiredAccess;

    @Value("${token.signing.key}")
    private String jwtSecret;

    public String generateJwtToken(AuthUser authUser) {
        Map<String, Object> claims = getClaims(authUser);
        return Jwts
                .builder()
                .claims().add(claims)
                .and()
                .subject(authUser.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredAccess))
                .signWith(getKey())
                .compact();
    }

    public Map<String, Object> getClaims(AuthUser authUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Claim.CREDENTIAL_ID.getValue(), authUser.getCredentialId());
        claims.put(Claim.USER_ROLE.getValue(), authUser.getRole());
        return claims;
    }

    public String generateRefreshToken() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public Boolean validateJwtToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        Claims claims = getClaims(token);

        boolean isTokenExpired = claims.getExpiration().before(new Date());
        return (username.equals(userDetails.getUsername())) && !isTokenExpired;
    }

    public Long getExpiredAccessToken(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().getTime() - System.currentTimeMillis();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public String getRole(String token) {
        Claims claims = getClaims(token);
        return claims.get(Claim.USER_ROLE.getValue(), String.class);
    }

    public Long getCredentialId(String token) {
        Claims claims = getClaims(token);
        return claims.get(Claim.CREDENTIAL_ID.getValue(), Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Getter
    private enum Claim {
        USER_ROLE("user_role"),
        ACCESS_EXPIRES("access_expires"),
        CREDENTIAL_ID("credential_id");

        private final String value;

        Claim(String value) {
            this.value = value;
        }
    }
}