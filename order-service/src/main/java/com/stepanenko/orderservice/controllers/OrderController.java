package com.stepanenko.orderservice.controllers;

import com.stepanenko.orderservice.dto.OrderRequestDto;
import com.stepanenko.orderservice.dto.OrderResponseDto;
import com.stepanenko.orderservice.services.OrderService;
import com.stepanenko.orderservice.util.OrderErrorResponse;
import com.stepanenko.orderservice.util.exceptions.StatusNotReceived;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "Order", description = "The order controller")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", description = "Oops! Something went wrong, we couldn't place your order(")
})
public class OrderController {

    private final OrderService orderService;


    @Operation(summary = "Places order with provided credentials, ticket id and order sum")
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
