package com.stepanenko.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "status-service")
public interface StatusClient {
    @GetMapping("/status")
    String getStatus(@RequestParam Long orderId);
}
