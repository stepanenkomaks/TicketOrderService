package com.stepanenko.ticketservice.services.interfaces;

import com.stepanenko.ticketservice.dto.BookedTicketResponseDto;

public interface BookedTicketServiceInt {
    BookedTicketResponseDto getTicketInfo(Long id);
}
