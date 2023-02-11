package com.stepanenko.orderservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info().title("Order service")
                        .description("Service that makes order and order ticket, and places them to the database." +
                                "Additionally, it connects to status service for receiving the status")
                        .contact(new Contact().name("stepanenko_maks")));
    }
}
