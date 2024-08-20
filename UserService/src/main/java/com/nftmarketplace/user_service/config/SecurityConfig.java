package com.nftmarketplace.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.nftmarketplace.user_service.config.jwt.JwtAuthenticateEntryPointImpl;
import com.nftmarketplace.user_service.config.jwt.JWTDecoderImpl;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
        @Bean
        SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {

                httpSecurity.csrf(csrf -> csrf.disable());

                httpSecurity.authorizeExchange(authorize -> authorize
                                .pathMatchers(HttpMethod.GET, "/user").authenticated()
                                .anyExchange().permitAll());

                httpSecurity.oauth2ResourceServer(oauth2 -> oauth2
                                .jwt(jwtConfigure -> jwtConfigure
                                                .jwtDecoder(new JWTDecoderImpl())));// Create 1 decoder
                // .jwtAuthenticationConverter(new AuthenticationConverterImpl())

                httpSecurity.exceptionHandling(ex -> ex
                                .authenticationEntryPoint(new JwtAuthenticateEntryPointImpl()));

                return httpSecurity.build();
        }
}
