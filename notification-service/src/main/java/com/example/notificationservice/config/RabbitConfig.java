package com.example.notificationservice.config;

import com.example.notificationservice.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class RabbitConfig {

    @Bean
    public Consumer<OrderEvent> orderEventConsumer() {
        return orderEvent -> log.info("Received an event: {}", orderEvent);
    }
}
