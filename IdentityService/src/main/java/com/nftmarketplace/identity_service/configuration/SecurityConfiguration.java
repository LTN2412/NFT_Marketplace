package com.nftmarketplace.identity_service.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.nftmarketplace.identity_service.exception.JwtAccessDeniedHandler;
import com.nftmarketplace.identity_service.exception.JwtAuthenticateEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

        private String prefixEndpoint = "identity/";
        private String[] PUBLIC_ENDPOINTS = { "user/register", "token", "introspect" };

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

                httpSecurity.csrf(AbstractHttpConfigurer::disable);

                httpSecurity.authorizeHttpRequests(request -> request
                                .requestMatchers(HttpMethod.POST, Arrays.stream(PUBLIC_ENDPOINTS)
                                                .map(endpoint -> prefixEndpoint + endpoint).toArray(String[]::new))
                                .permitAll()
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
