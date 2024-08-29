package com.marketplace.notification_service.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

@Configuration
public class WebSocketConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:5173");
        // .allowCredentials(true);
        WebFluxConfigurer.super.addCorsMappings(registry);
    }

    @Bean
    SimpleUrlHandlerMapping webSocketMapping(WebSocketHandler handler) {
        return new SimpleUrlHandlerMapping(Map.of("/ws/notification", handler), -1);
    }
}
