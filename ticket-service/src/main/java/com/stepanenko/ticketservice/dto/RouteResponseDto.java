package com.stepanenko.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteResponseDto {
    private Long id;

    private String placeFrom;

    private String placeTo;

    private Date departureTime;

    private int price;

    private int availableTickets;
}
