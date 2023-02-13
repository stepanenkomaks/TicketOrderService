package com.stepanenko.ticketservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info().title("Ticket service")
                        .description("Here you can mage routes and tickets, and book a ticket from a particular route")
                        .contact(new Contact().name("stepanenko_maks")));
    }
}
