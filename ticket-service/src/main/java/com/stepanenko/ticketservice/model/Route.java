package com.stepanenko.ticketservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "routes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String placeFrom;

    @Column(nullable = false)
    private String placeTo;

    @Column(nullable = false)
    private Date departureTime;

    @Column(nullable = false)
    private int price;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<FreeTicket> freeTickets;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<BookedTicket> bookedTickets;
}
