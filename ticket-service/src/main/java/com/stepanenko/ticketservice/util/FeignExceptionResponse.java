package com.stepanenko.ticketservice.util;

import io.swagger.v3.oas.annotations.media.Schema;

public record FeignExceptionResponse(@Schema(example = "Oops! Something went wrong, we couldn't place your order(") String message) {
}
