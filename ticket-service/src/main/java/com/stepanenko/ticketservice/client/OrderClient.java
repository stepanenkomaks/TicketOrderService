package com.stepanenko.ticketservice.client;

import com.stepanenko.ticketservice.dto.OrderInfoResponseDto;
import com.stepanenko.ticketservice.dto.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service")
public interface OrderClient {
    @PostMapping ("/order")
    OrderInfoResponseDto placeOrder(@RequestBody OrderRequestDto orderRequestDto);
}
