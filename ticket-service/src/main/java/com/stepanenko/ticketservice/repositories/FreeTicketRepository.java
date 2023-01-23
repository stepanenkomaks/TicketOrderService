package com.stepanenko.ticketservice.repositories;

import com.stepanenko.ticketservice.model.FreeTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeTicketRepository extends JpaRepository<FreeTicket, Long> {
}
