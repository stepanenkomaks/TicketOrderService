package com.stepanenko.orderservice.services.interfaces;

import com.stepanenko.orderservice.dto.OrderRequestDto;
import com.stepanenko.orderservice.dto.OrderResponseDto;

public interface OrderServiceInt {
    OrderResponseDto placeOrder(OrderRequestDto orderRequestDto);
}
