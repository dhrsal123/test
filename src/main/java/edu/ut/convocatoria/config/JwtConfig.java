package edu.ut.convocatoria.config;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;


@Setter
@Getter
@Configuration
public class JwtConfig {

    @Value("${convocatoria.jwt.expiration-time}")
    private Long expirationTime;

    @Value("${convocatoria.jwt.secret}")
    private String secret;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }


}
