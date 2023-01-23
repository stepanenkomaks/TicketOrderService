package com.stepanenko.orderservice.services;

import com.stepanenko.orderservice.services.interfaces.GetStatusServiceInt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class GetStatusService implements GetStatusServiceInt {

    private final RestTemplate restTemplate;


    public String getStatus(Long orderId) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl("http://localhost:8083/status")
                .queryParam("orderId", String.valueOf(orderId))
                .encode()
                .toUriString();

        return restTemplate.getForObject(urlTemplate, String.class);
    }
}
