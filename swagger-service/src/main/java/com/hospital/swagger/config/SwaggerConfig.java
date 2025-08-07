package com.hospital.swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    
    @Value("${server.port:8084}")
    private String serverPort;
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                   .info(new Info()
                             .title("MediFlow API Documentation")
                             .description("Comprehensive API documentation for MediFlow - International Patient Treatment Management System")
                             .version("1.0.0")
                             .contact(new Contact()
                                          .name("MediFlow Support")
                                          .email("support@mediflow.com")
                                          .url("https://mediflow.com"))
                             .license(new License()
                                          .name("MIT License")
                                          .url("https://opensource.org/licenses/MIT")))
                   .servers(List.of(
                       new Server()
                           .url("http://localhost:" + serverPort)
                           .description("Local Development Server"),
                       new Server()
                           .url("https://mediflow-swagger.render.com")
                           .description("Production Server")));
    }
}