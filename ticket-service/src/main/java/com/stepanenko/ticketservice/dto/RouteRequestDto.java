package com.stepanenko.ticketservice.dto;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RouteRequestDto {
    private String placeFrom;

    private String placeTo;

    private Date departureTime;

    @Min(value = 1)
    private int price;
}
