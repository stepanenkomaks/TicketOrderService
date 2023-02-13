package com.stepanenko.ticketservice.controllers;

import com.stepanenko.ticketservice.dto.FreeTicketResponseDto;
import com.stepanenko.ticketservice.dto.TicketRequestDto;
import com.stepanenko.ticketservice.dto.BookedTicketResponseDto;
import com.stepanenko.ticketservice.services.BookedTicketService;
import com.stepanenko.ticketservice.services.FreeTicketService;
import com.stepanenko.ticketservice.util.TicketErrorResponse;
import com.stepanenko.ticketservice.util.exceptions.RouteNotFoundException;
import com.stepanenko.ticketservice.util.exceptions.TicketNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Tag(name = "Route", description = "The route controller")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", description = "No such route"),
})
public class TicketController {

    private final BookedTicketService bookedTicketService;

    private final FreeTicketService freeTicketService;

    @ApiResponse(responseCode = "404", description = "Ticket not found!")
    @Operation(summary = "Get ticket info by ticket id")
    @GetMapping("/{id}")
    public ResponseEntity<BookedTicketResponseDto> getTicketInfo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookedTicketService.getTicketInfo(id));
    }

    @Operation(summary = "Add new ticket for existing route")
    @PostMapping()
    public ResponseEntity<FreeTicketResponseDto> saveTicket(@RequestBody TicketRequestDto ticketRequestDto) {
        return new ResponseEntity<>(freeTicketService.save(ticketRequestDto), HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<TicketErrorResponse> handleException(RouteNotFoundException e) {
        TicketErrorResponse response = new TicketErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<TicketErrorResponse> handleException(TicketNotFoundException e) {
        TicketErrorResponse response = new TicketErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
