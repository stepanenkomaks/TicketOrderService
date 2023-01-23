package com.stepanenko.ticketservice.controllers;

import com.stepanenko.ticketservice.dto.FreeTicketResponseDto;
import com.stepanenko.ticketservice.dto.TicketRequestDto;
import com.stepanenko.ticketservice.dto.BookedTicketResponseDto;
import com.stepanenko.ticketservice.services.BookedTicketService;
import com.stepanenko.ticketservice.services.FreeTicketService;
import com.stepanenko.ticketservice.util.TicketErrorResponse;
import com.stepanenko.ticketservice.util.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final BookedTicketService bookedTicketService;

    private final FreeTicketService freeTicketService;

    @GetMapping("/{id}")
    public ResponseEntity<BookedTicketResponseDto> getTicketInfo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookedTicketService.getTicketInfo(id));
    }

    @PostMapping("/new")
    public ResponseEntity<FreeTicketResponseDto> saveTicket(@RequestBody TicketRequestDto ticketRequestDto) {
        return new ResponseEntity<>(freeTicketService.save(ticketRequestDto), HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<TicketErrorResponse> handleException(DataNotFoundException e) {
        TicketErrorResponse response = new TicketErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
