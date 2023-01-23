package com.stepanenko.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderTicket {
    @Id
    private Long id;

    private String credentials;

    @OneToMany(mappedBy = "orderTicket", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
}
