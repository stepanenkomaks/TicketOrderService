package com.stepanenko.ticketservice.services.interfaces;

import com.stepanenko.ticketservice.dto.OrderInfoResponseDto;

public interface GetOrderInfoServiceInt {
    public OrderInfoResponseDto getOrderInfo(String credentials, Integer routePrice, Long bookedTicketId);
}
