package com.stepanenko.ticketservice.services.interfaces;

import com.stepanenko.ticketservice.dto.FreeTicketResponseDto;
import com.stepanenko.ticketservice.dto.TicketRequestDto;

public interface FreeTicketServiceInt {
    FreeTicketResponseDto save(TicketRequestDto ticketRequestDto);
}
