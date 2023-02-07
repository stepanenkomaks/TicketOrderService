package com.stepanenko.orderservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "status-service")
public interface StatusClient {
    @GetMapping("/status")
    @CircuitBreaker(name = "status", fallbackMethod = "defaultStatus")
    String getStatus(@RequestParam Long orderId);

    default String defaultStatus(Long orderId, Throwable throwable) {
        return "Default";
    }
}
