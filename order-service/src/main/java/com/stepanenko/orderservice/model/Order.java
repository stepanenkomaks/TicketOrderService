package com.stepanenko.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String credentials;

    @Column(nullable = false)
    private Integer sum;

    private String status;

    @ManyToOne
    @JoinColumn(name = "order_ticket_id", referencedColumnName = "id")
    private OrderTicket orderTicket;
}
