package com.stepanenko.ticketservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "booked_tickets")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BookedTicket {
    @Id
    private Long id;

    @Column(nullable = false)
    private String credentials;

    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Route route;
}
