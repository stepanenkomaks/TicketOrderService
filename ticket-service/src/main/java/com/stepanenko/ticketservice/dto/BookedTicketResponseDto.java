package com.stepanenko.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookedTicketResponseDto {

    private String credentials;

    private String orderStatus;

    private RouteResponseDto route;
}
