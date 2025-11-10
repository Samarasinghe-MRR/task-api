package com.example.task_api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Marks this class as a source of bean definitions
public class WebConfig implements WebMvcConfigurer {

    // Environment-based CORS origins with fallback to localhost for development
    @Value("${CORS_ALLOWED_ORIGINS:http://localhost:3000,http://localhost:5173}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        // Split comma-separated origins and convert to array
        String[] origins = allowedOrigins.split(",");

        registry.addMapping("/api/**") // Allow CORS for all /api paths
                .allowedOrigins(origins) // Dynamic origins based on environment
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
                .allowCredentials(true); // Needed if using sessions or authentication headers
    }
}