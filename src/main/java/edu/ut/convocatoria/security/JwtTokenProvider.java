package edu.ut.convocatoria.security;

import edu.ut.convocatoria.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final JwtParser jwtParser;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.jwtParser = Jwts.parser()
                .verifyWith(jwtProperties.getKey())
                .build();
    }

    public String generateToken(Authentication authentication) {
        var userPrincipal = (User) authentication.getPrincipal();

        if(Objects.isNull(userPrincipal)){
            return null;
        }

        final var now = Instant.now();
        final var plusMillis = now.plusMillis(jwtProperties.getExpirationTime());
        final var expirationDate = Date.from(
                plusMillis
        );

        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(Date.from(now))
                .expiration(expirationDate)
                .signWith(jwtProperties.getKey())
                .compact();
    }

    public String getUserIdFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return parseClaims(token).getExpiration();
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            log.info("Invalid JWT token: {}", e.getMessage());
            return false;
        }

    }

    private Claims parseClaims(String token) {
        return jwtParser.parseSignedClaims(token)
                .getPayload();
    }


}
