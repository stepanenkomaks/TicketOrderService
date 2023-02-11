package com.stepanenko.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {

    @Schema(example = "Stepanenko Maks")
    private String credentials;

    @Schema(example = "200")
    private Integer sum;

    @Schema(example = "1")
    private Long ticketId;
}
