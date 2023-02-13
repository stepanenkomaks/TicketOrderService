package com.stepanenko.ticketservice.controllers;

import com.stepanenko.ticketservice.dto.RouteRequestDto;
import com.stepanenko.ticketservice.dto.RouteResponseDto;
import com.stepanenko.ticketservice.dto.TakeTicketResponse;
import com.stepanenko.ticketservice.services.RouteService;
import com.stepanenko.ticketservice.util.RouteErrorResponse;
import com.stepanenko.ticketservice.util.FeignExceptionResponse;
import com.stepanenko.ticketservice.util.TicketErrorResponse;
import com.stepanenko.ticketservice.util.exceptions.RouteNotFoundException;
import com.stepanenko.ticketservice.util.exceptions.TicketNotFoundException;
import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
@Tag(name = "Route", description = "The route controller")


@ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", description = "Route not found!"),
})


public class RouteController {

    private final RouteService routeService;

    @Operation(summary = "Book a ticket for this route for provided credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "503", description = "Oops! Something went wrong, we couldn't place your order("),
            @ApiResponse(responseCode = "404", description = "There are no available tickets for this route!")
    })
    @PatchMapping("/{id}/take")
    public ResponseEntity<TakeTicketResponse> takeTicket(@PathVariable("id") Long id, @RequestParam String credentials) {
        return ResponseEntity.ok(routeService.takeTicket(credentials, id));
    }

    @Operation(summary = "Get a list of routes")
    @GetMapping()
    public ResponseEntity<List<RouteResponseDto>> getAllRoutes() {
        return ResponseEntity.ok(routeService.findAll());
    }

    @Operation(summary = "Get a route info by route id")
    @GetMapping("/{id}")
    public ResponseEntity<RouteResponseDto> getRouteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(routeService.findById(id));
    }

    @Operation(summary = "Add new route")
    @PostMapping()
    public ResponseEntity<RouteResponseDto> saveRoute(@RequestBody RouteRequestDto routeRequestDto) {
        return new ResponseEntity<>(routeService.save(routeRequestDto), HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<RouteErrorResponse> handleException(RouteNotFoundException e) {
        RouteErrorResponse response = new RouteErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<TicketErrorResponse> handleException(TicketNotFoundException e) {
        TicketErrorResponse response = new TicketErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<FeignExceptionResponse> handleFeignStatusException(FeignException e) {
        String message = "Oops! Something went wrong, we couldn't place your order(";
        FeignExceptionResponse response = new FeignExceptionResponse(message);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(e.status()));
    }
}
