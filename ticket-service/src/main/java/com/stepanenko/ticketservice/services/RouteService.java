package com.stepanenko.ticketservice.services;

import com.stepanenko.ticketservice.dto.RouteRequestDto;
import com.stepanenko.ticketservice.dto.RouteResponseDto;
import com.stepanenko.ticketservice.model.BookedTicket;
import com.stepanenko.ticketservice.model.FreeTicket;
import com.stepanenko.ticketservice.model.Route;
import com.stepanenko.ticketservice.repositories.BookedTicketRepository;
import com.stepanenko.ticketservice.repositories.FreeTicketRepository;
import com.stepanenko.ticketservice.repositories.RouteRepository;
import com.stepanenko.ticketservice.services.interfaces.RouteServiceInt;
import com.stepanenko.ticketservice.util.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RouteService implements RouteServiceInt {
    private final RouteRepository routeRepository;

    private final FreeTicketRepository freeTicketRepository;

    private final BookedTicketRepository bookedTicketRepository;

    private final GetOrderInfoService getOrderInfoService;

    public List<RouteResponseDto> findAll() {
        return routeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public RouteResponseDto mapToResponse(Route route) {
        return RouteResponseDto.builder()
                .id(route.getId())
                .placeFrom(route.getPlaceFrom())
                .placeTo(route.getPlaceTo())
                .departureTime(route.getDepartureTime())
                .price(route.getPrice())
                .availableTickets(route.getFreeTickets() == null ? 0 : route.getFreeTickets().size())
                .build();
    }

    public RouteResponseDto findById(long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Route not found!"));
        return mapToResponse(route);
    }

    @Transactional
    public RouteResponseDto save(RouteRequestDto routeRequestDto) {
        Route route = mapToRoute(routeRequestDto);
        routeRepository.save(route);
        return mapToResponse(route);
    }

    private Route mapToRoute(RouteRequestDto routeRequestDto) {
        return Route.builder()
                .placeFrom(routeRequestDto.getPlaceFrom())
                .placeTo(routeRequestDto.getPlaceTo())
                .price(routeRequestDto.getPrice())
                .departureTime(routeRequestDto.getDepartureTime())
                .build();
    }

    @SneakyThrows
    @Transactional
    public Long takeTicket(String credentials, long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Route not found!"));

        List<FreeTicket> tickets = route.getFreeTickets();

        if (tickets.isEmpty())
            throw new DataNotFoundException("There are no available tickets for this route!");

        FreeTicket freeTicket = tickets.get(tickets.size() - 1);
        BookedTicket bookedTicket = BookedTicket.builder()
                .id(freeTicket.getId())
                .credentials(credentials)
                .route(freeTicket.getRoute())
                .build();

        String status = getOrderInfoService.getOrderInfo(credentials, route.getPrice(), bookedTicket.getId()).status();

        return statusHandler(status, route, freeTicket, bookedTicket);
    }

    private Long statusHandler(String status, Route route, FreeTicket freeTicket, BookedTicket bookedTicket) {
        if (status.equals("FAILED")) {
            log.info("Returning total of free seats because order status was FAILED");
            return (long) route.getFreeTickets().size();
        }
        else {
            log.info("Returning id of the ticket. Order status was DONE");
            route.getFreeTickets().remove(freeTicket);
            bookedTicket.setOrderStatus(status);
            freeTicketRepository.deleteById(freeTicket.getId());
            bookedTicketRepository.save(bookedTicket);

            return bookedTicket.getId();
        }
    }

}
