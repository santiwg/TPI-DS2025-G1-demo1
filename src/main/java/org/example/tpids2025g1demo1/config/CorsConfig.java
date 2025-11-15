package org.example.tpids2025g1demo1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // aplica a todos los endpoints
                        // Aceptar cualquier origen (útil para Postman/Thunder y ambientes locales)
                        .allowedOriginPatterns("*")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // métodos permitidos
                        .allowedHeaders("*") // cabeceras permitidas
                        .allowCredentials(true)
                        .maxAge(3600); // cache del preflight (OPTIONS)
            }
        };
    }
}
