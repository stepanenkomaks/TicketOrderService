package com.stepanenko.ticketservice.services;

import com.stepanenko.ticketservice.dto.BookedTicketResponseDto;
import com.stepanenko.ticketservice.dto.RouteResponseDto;
import com.stepanenko.ticketservice.model.BookedTicket;
import com.stepanenko.ticketservice.model.Route;
import com.stepanenko.ticketservice.repositories.BookedTicketRepository;
import com.stepanenko.ticketservice.services.interfaces.BookedTicketServiceInt;
import com.stepanenko.ticketservice.util.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookedTicketService implements BookedTicketServiceInt {

    private final BookedTicketRepository bookedTicketRepository;

    public BookedTicketResponseDto getTicketInfo(Long id) {
        BookedTicket ticket = bookedTicketRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Ticket not found!"));

        Route route = ticket.getRoute();

        RouteResponseDto routeResponseDto = RouteResponseDto.builder()
                .id(route.getId())
                .placeFrom(route.getPlaceFrom())
                .placeTo(route.getPlaceTo())
                .departureTime(route.getDepartureTime())
                .price(route.getPrice())
                .availableTickets(route.getFreeTickets().size())
                .build();

        return BookedTicketResponseDto.builder()
                .credentials(ticket.getCredentials())
                .orderStatus(ticket.getOrderStatus())
                .route(routeResponseDto)
                .build();
    }
}
