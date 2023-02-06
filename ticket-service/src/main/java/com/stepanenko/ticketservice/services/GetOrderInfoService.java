package com.stepanenko.ticketservice.services;

import com.stepanenko.ticketservice.client.OrderClient;
import com.stepanenko.ticketservice.dto.OrderInfoResponseDto;
import com.stepanenko.ticketservice.dto.OrderRequestDto;
import com.stepanenko.ticketservice.services.interfaces.GetOrderInfoServiceInt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrderInfoService implements GetOrderInfoServiceInt {

    private final OrderClient orderClient;


//    public OrderInfoResponseDto getOrderInfo(String credentials, Integer routePrice, Long bookedTicketId) {
//        Map<String, String> bodyParams = new HashMap<>();
//        bodyParams.put("credentials", credentials);
//        bodyParams.put("sum", String.valueOf(routePrice));
//        bodyParams.put("ticketId", String.valueOf(bookedTicketId));
//
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<Map<String, String>> request = new  HttpEntity<>(bodyParams, headers);
//
//        return restTemplate.postForObject("http://localhost:8082/order", request, OrderInfoResponseDto.class);
//    }

    public OrderInfoResponseDto getOrderInfo(String credentials, Integer routePrice, Long bookedTicketId) {
        OrderRequestDto requestDto = OrderRequestDto.builder()
                .credentials(credentials)
                .sum(routePrice)
                .ticketId(bookedTicketId)
                .build();

        return orderClient.placeOrder(requestDto);
    }
}
