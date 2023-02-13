package com.stepanenko.ticketservice.services;

import com.stepanenko.ticketservice.dto.FreeTicketResponseDto;
import com.stepanenko.ticketservice.dto.RouteResponseDto;
import com.stepanenko.ticketservice.dto.TicketRequestDto;
import com.stepanenko.ticketservice.model.FreeTicket;
import com.stepanenko.ticketservice.model.Route;
import com.stepanenko.ticketservice.repositories.FreeTicketRepository;
import com.stepanenko.ticketservice.repositories.RouteRepository;
import com.stepanenko.ticketservice.services.interfaces.FreeTicketServiceInt;
import com.stepanenko.ticketservice.util.exceptions.RouteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FreeTicketService implements FreeTicketServiceInt {

    private final FreeTicketRepository freeTicketRepository;

    private final RouteRepository routeRepository;

    @Transactional
    public FreeTicketResponseDto save(TicketRequestDto ticketRequestDto) {
        FreeTicket ticket = mapToTicket(ticketRequestDto);
        freeTicketRepository.save(ticket);
        return mapToResponse(ticket);
    }

    private FreeTicket mapToTicket(TicketRequestDto ticketRequestDto) {
        Route route = routeRepository.findById(ticketRequestDto.getRouteId())
                .orElseThrow(() -> new RouteNotFoundException("No such route"));
        return FreeTicket.builder()
                .route(route)
                .build();
    }

    private FreeTicketResponseDto mapToResponse(FreeTicket freeTicket) {
        Route route = freeTicket.getRoute();

        RouteResponseDto routeResponseDto = RouteResponseDto.builder()
                .id(route.getId())
                .placeFrom(route.getPlaceFrom())
                .placeTo(route.getPlaceTo())
                .departureTime(route.getDepartureTime())
                .price(route.getPrice())
                .availableTickets(route.getFreeTickets().size())
                .build();

        return FreeTicketResponseDto.builder()
                .id(freeTicket.getId())
                .route(routeResponseDto)
                .build();
    }
}
