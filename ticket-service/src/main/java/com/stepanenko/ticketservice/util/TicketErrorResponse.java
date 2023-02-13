package com.stepanenko.ticketservice.util;

import io.swagger.v3.oas.annotations.media.Schema;

public record TicketErrorResponse(@Schema(example = "Ticket not found!") String message) {
}
