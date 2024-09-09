package com.nftmarketplace.asset_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.nftmarketplace.asset_service.config.jwt.JWTDecoderImpl;
import com.nftmarketplace.asset_service.config.jwt.JwtAccessDenniedHandler;
import com.nftmarketplace.asset_service.config.jwt.JwtAuthenticateEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(authorize -> authorize
                                // .requestMatchers(HttpMethod.POST, "/comment").authenticated()
                                .anyRequest().permitAll());

                http.oauth2ResourceServer(oauth2 -> oauth2
                                .jwt(jwtConfig -> jwtConfig
                                                .decoder(new JWTDecoderImpl())
                                                .jwtAuthenticationConverter(jwtAuthenticationConverter())));

                http.exceptionHandling(ex -> ex
                                .accessDeniedHandler(new JwtAccessDenniedHandler())
                                .authenticationEntryPoint(new JwtAuthenticateEntryPoint()));

                return http.build();
        }

        @Bean
        JwtAuthenticationConverter jwtAuthenticationConverter() {
                JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                grantedAuthoritiesConverter.setAuthoritiesClaimName("scope");
                grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
                JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
                jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
                return jwtAuthenticationConverter;
        }
}
