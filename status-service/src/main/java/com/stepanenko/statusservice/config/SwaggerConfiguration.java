package com.stepanenko.statusservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info().title("Status service")
                        .description("Service that randomly returns status FAIL/DONE/NEW for your order id")
                        .contact(new Contact().name("stepanenko_maks")));
    }
}
