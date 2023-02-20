package com.stepanenko.orderservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetStatusClient {

    private final StatusClient statusClient;

    public String getStatus(Long orderId) {
        return statusClient.getStatus(orderId);
    }
}
