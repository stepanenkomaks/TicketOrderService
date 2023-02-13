package com.stepanenko.ticketservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Dnipro")
    private String placeFrom;

    @Schema(example = "Lviv")
    private String placeTo;

    @Schema(example = "1646172000000")
    private Date departureTime;

    @Schema(example = "200")
    private int price;

    @Schema(example = "5")
    private int availableTickets;
}
