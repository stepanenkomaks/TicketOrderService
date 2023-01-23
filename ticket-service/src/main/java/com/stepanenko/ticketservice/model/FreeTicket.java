package com.stepanenko.ticketservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "free_tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreeTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Route route;
}
