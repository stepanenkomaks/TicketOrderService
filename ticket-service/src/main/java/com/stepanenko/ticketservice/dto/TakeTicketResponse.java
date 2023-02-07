package com.stepanenko.ticketservice.dto;

import lombok.*;

//Value is either num of free tickets or ID of booked ticket
public record TakeTicketResponse (String message, Long value) {
}
