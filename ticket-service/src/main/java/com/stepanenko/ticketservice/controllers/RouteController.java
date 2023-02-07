package com.stepanenko.ticketservice.controllers;

import com.stepanenko.ticketservice.dto.RouteRequestDto;
import com.stepanenko.ticketservice.dto.RouteResponseDto;
import com.stepanenko.ticketservice.dto.TakeTicketResponse;
import com.stepanenko.ticketservice.services.RouteService;
import com.stepanenko.ticketservice.util.RouteErrorResponse;
import com.stepanenko.ticketservice.util.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PatchMapping("/{id}/take")
    public ResponseEntity<TakeTicketResponse> takeTicket(@PathVariable("id") Long id, @RequestParam String credentials) {
        return ResponseEntity.ok(routeService.takeTicket(credentials, id));
    }

    @GetMapping()
    public ResponseEntity<List<RouteResponseDto>> getAllRoutes() {
        return ResponseEntity.ok(routeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteResponseDto> getRouteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(routeService.findById(id));
    }

    @PostMapping("/new")
    public ResponseEntity<RouteResponseDto> saveRoute(@RequestBody RouteRequestDto routeRequestDto) {
        return new ResponseEntity<>(routeService.save(routeRequestDto), HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<RouteErrorResponse> handleException(DataNotFoundException e) {
        RouteErrorResponse response = new RouteErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
