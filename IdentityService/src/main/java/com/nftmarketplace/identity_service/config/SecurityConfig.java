package com.nftmarketplace.identity_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(10);
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.cors(AbstractHttpConfigurer::disable);

                httpSecurity.csrf(AbstractHttpConfigurer::disable);

                httpSecurity.authorizeHttpRequests(request -> request
                                .anyRequest()
                                .permitAll());

                return httpSecurity.build();
        }
}
