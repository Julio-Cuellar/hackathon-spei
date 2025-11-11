package com.cuellar.authservice.service;


import com.cuellar.authservice.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secret;  // Puede ser Base64 o texto plano (≥32 bytes)

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.audience}")
    private String audience;

    @Value("${security.jwt.access-minutes:10}")
    private long accessMinutes; // Duración del access token en MINUTOS

    private Key getKey() {
        byte[] keyBytes;
        // Intenta decodificar como Base64 primero
        try {
            keyBytes = Decoders.BASE64.decode(secret.trim());
        } catch (IllegalArgumentException ex) {
            // Si no es Base64, usa bytes del string
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        if (keyBytes.length < 32) { // 32 bytes = 256 bits para HS256
            throw new WeakKeyException("JWT secret demasiado corto (" + (keyBytes.length * 8)
                    + " bits). HS256 requiere >= 256 bits. Usa una cadena Base64 de 32+ bytes.");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /** Genera un JWT (HS256) que expira en 'accessMinutes' minutos. */
    public String generateToken(User user) {
        Instant now = Instant.now();
        Instant exp = now.plus(Duration.ofMinutes(accessMinutes));

        return Jwts.builder()
                .subject(user.getUsername())              // subject = username (puedes cambiar a userId si prefieres)
                .claim("userId", user.getId())            // id como claim// rol (enum) como string
                .issuer(issuer)
                .audience().add(audience).and()
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))               // ✅ minutos reales
                .signWith(getKey())
                .compact();
    }

    /** Extrae el username (subject) del token. */
    public String extractUsername(String token) {
        return parse(token).getPayload().getSubject();
    }

    /** Valida firma, issuer, audience y expiración. */
    public boolean isTokenValid(String token) {
        try {
            parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .requireIssuer(issuer)
                .requireAudience(audience)
                .build()
                .parseSignedClaims(token);
    }
}
