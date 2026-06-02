package edu.ut.convocatoria.config;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;


@Setter
@Getter
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "convocatoria.jwt")
public class JwtProperties {

    private Long expirationTime;

    private String secret;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }


}
