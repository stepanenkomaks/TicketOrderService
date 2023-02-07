package com.stepanenko.orderservice.controllers;

import com.stepanenko.orderservice.dto.OrderRequestDto;
import com.stepanenko.orderservice.dto.OrderResponseDto;
import com.stepanenko.orderservice.services.OrderService;
import com.stepanenko.util.OrderErrorResponse;
import com.stepanenko.util.exceptions.StatusNotReceived;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return new ResponseEntity<>(orderService.placeOrder(orderRequestDto), HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<OrderErrorResponse> handleException(StatusNotReceived e) {
        OrderErrorResponse response = new OrderErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
