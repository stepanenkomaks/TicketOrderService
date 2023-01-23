package com.stepanenko.ticketservice.repositories;

import com.stepanenko.ticketservice.model.BookedTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookedTicketRepository extends JpaRepository<BookedTicket, Long> {
}
