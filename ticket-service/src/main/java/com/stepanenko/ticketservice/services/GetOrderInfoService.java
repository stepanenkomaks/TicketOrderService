package com.stepanenko.ticketservice.services;

import com.stepanenko.ticketservice.dto.OrderInfoResponseDto;
import com.stepanenko.ticketservice.services.interfaces.GetOrderInfoServiceInt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetOrderInfoService implements GetOrderInfoServiceInt {

    private final RestTemplate restTemplate;

    private final HttpHeaders headers;

    public OrderInfoResponseDto getOrderInfo(String credentials, Integer routePrice, Long bookedTicketId) {
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("credentials", credentials);
        bodyParams.put("sum", String.valueOf(routePrice));
        bodyParams.put("ticketId", String.valueOf(bookedTicketId));

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new  HttpEntity<>(bodyParams, headers);

        return restTemplate.postForObject("http://localhost:8082/order", request, OrderInfoResponseDto.class);
    }
}
