package com.serasa.registerandscore.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authConfigs())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @NonNull
    private static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authConfigs() {
        return auth -> auth
                .requestMatchers(allowedRoutes()).permitAll()
                .requestMatchers(HttpMethod.POST, authRoutes()).permitAll()
                .requestMatchers(HttpMethod.POST, "/v1/api/person").hasAuthority("SCOPE_ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/v1/api/person/**").hasAuthority("SCOPE_ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/v1/api/person/**").hasAuthority("SCOPE_ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/v1/api/person").authenticated()
                .anyRequest().authenticated();
    }

    @NonNull
    private static String[] authRoutes() {
        return new String[]{"/v1/api/auth/login", "/v1/api/auth/register"};
    }

    @NonNull
    private static String[] allowedRoutes() {
        return new String[]{
                "/v3/api-docs/**", 
                "/swagger-ui/**", 
                "/swagger-ui.html", 
                "/h2-console/**"
        };
    }
}
