package com.stepanenko.ticketservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookedTicketResponseDto {

    @Schema(example = "Stepanenko Maks")
    private String credentials;

    @Schema(example = "DONE")
    private String orderStatus;

    private RouteResponseDto route;
}
