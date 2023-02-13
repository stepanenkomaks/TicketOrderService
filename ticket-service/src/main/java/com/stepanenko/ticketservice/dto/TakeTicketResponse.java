package com.stepanenko.ticketservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

//Value is either num of free tickets or ID of booked ticket
public record TakeTicketResponse(@Schema(example = "Returning total of free seats because order status was FAILED") String message,
                                  @Schema(example = "5") Long value) {
}
