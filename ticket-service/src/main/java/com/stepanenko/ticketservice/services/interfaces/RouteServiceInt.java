package com.stepanenko.ticketservice.services.interfaces;

import com.stepanenko.ticketservice.dto.RouteRequestDto;
import com.stepanenko.ticketservice.dto.RouteResponseDto;
import com.stepanenko.ticketservice.dto.TakeTicketResponse;

import java.util.List;

public interface RouteServiceInt {
    List<RouteResponseDto> findAll();
    RouteResponseDto findById(long id);
    TakeTicketResponse takeTicket(String credentials, long id);

    RouteResponseDto save(RouteRequestDto routeRequestDto);

}
