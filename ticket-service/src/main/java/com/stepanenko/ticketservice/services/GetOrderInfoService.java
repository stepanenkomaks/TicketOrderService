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

    public OrderInfoResponseDto getOrderInfo(String credentials, Integer routePrice, Long bookedTicketId) {
        OrderRequestDto requestDto = OrderRequestDto.builder()
                .credentials(credentials)
                .sum(routePrice)
                .ticketId(bookedTicketId)
                .build();

        return orderClient.placeOrder(requestDto);
    }
}
