package com.serasa.registerandscore.core.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtConfig {

    private final String jwtSecret = "this-is-a-very-secret-key-that-must-be-at-least-256-bits-long!!";

    @Bean
    public JwtEncoder jwtEncoder() {
        var secretKey = buildSecretKey();
        var immutableSecret = new ImmutableSecret<>(secretKey);
        return new NimbusJwtEncoder(immutableSecret);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        var secretKey = buildSecretKey();
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @NonNull
    private SecretKeySpec buildSecretKey() {
        return new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA256");
    }
}
