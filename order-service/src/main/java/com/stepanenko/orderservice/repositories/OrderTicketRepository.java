package com.stepanenko.orderservice.repositories;

import com.stepanenko.orderservice.model.OrderTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTicketRepository extends JpaRepository<OrderTicket, Long> {
}
