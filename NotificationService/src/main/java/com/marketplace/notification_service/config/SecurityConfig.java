package com.marketplace.notification_service.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.marketplace.notification_service.config.jwt.JWTDecoderImpl;
import com.marketplace.notification_service.config.jwt.JwtAccessDenniedHandler;
import com.marketplace.notification_service.config.jwt.JwtAuthenticateEntryPoint;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
        @Bean
        SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
                httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()));

                httpSecurity.csrf(csrf -> csrf.disable());

                httpSecurity.authorizeExchange(authorize -> authorize
                                .pathMatchers("/ws/**").permitAll()
                                .anyExchange().authenticated());

                httpSecurity.oauth2ResourceServer(oauth2 -> oauth2
                                .jwt(jwtConfigure -> jwtConfigure
                                                .jwtDecoder(new JWTDecoderImpl())
                                                .jwtAuthenticationConverter(grantedAuthoritiesExtractor())));

                httpSecurity.exceptionHandling(ex -> ex
                                .authenticationEntryPoint(new JwtAuthenticateEntryPoint())
                                .accessDeniedHandler(new JwtAccessDenniedHandler()));
                return httpSecurity.build();
        }

        @Bean
        ReactiveJwtAuthenticationConverterAdapter grantedAuthoritiesExtractor() {
                JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("scope");
                jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
                JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
                jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
                return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                configuration.setAllowCredentials(true);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

}
