package com.stepanenko.ticketservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RouteRequestDto {

    @Schema(example = "Dnipro")
    private String placeFrom;

    @Schema(example = "Lviv")
    private String placeTo;

    @Schema(example = "2023-04-03")
    private Date departureTime;

    @Schema(example = "200")
    @Min(value = 1)
    private int price;
}
