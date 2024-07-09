package com.nftmartketplace.identity_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.nftmartketplace.identity_service.exception.JwtAccessDeniedHandler;
import com.nftmartketplace.identity_service.exception.JwtAuthenticateEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

        private String[] PUBLIC_ENDPOINTS = { "register", "token", "introspect", "test" };

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.csrf(AbstractHttpConfigurer::disable);

                httpSecurity.authorizeHttpRequests(request -> request
                                .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                                .anyRequest()
                                .authenticated());

                httpSecurity.oauth2ResourceServer(oauth2 -> oauth2
                                .jwt(jwtConfigure -> jwtConfigure
                                                .decoder(new CustomJwtDecoder())));// Create 1 decoder

                httpSecurity.exceptionHandling(ex -> ex
                                .authenticationEntryPoint(new JwtAuthenticateEntryPoint())// Catch error if token not
                                                                                          // valid
                                .accessDeniedHandler(new JwtAccessDeniedHandler()));// Catch error if don't have
                                                                                    // permission

                return httpSecurity.build();
        }
}
