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
public class FreeTicketResponseDto {

    @Schema(example = "5")
    private Long id;

    private RouteResponseDto route;
}
