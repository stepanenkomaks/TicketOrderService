package com.stepanenko.orderservice.services;

import com.stepanenko.orderservice.client.StatusClient;
import com.stepanenko.orderservice.dto.OrderRequestDto;
import com.stepanenko.orderservice.dto.OrderResponseDto;
import com.stepanenko.orderservice.model.Order;
import com.stepanenko.orderservice.model.OrderTicket;
import com.stepanenko.orderservice.repositories.OrderRepository;
import com.stepanenko.orderservice.repositories.OrderTicketRepository;
import com.stepanenko.orderservice.services.interfaces.OrderServiceInt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService implements OrderServiceInt {

    private final OrderRepository orderRepository;

    private final OrderTicketRepository orderTicketRepository;

    private final ScheduledHandler scheduledHandler;

    private final StatusClient statusClient;

    @Transactional
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {
        Order order = createOrderAndTicket(orderRequestDto);

        String status = statusClient.getStatus(order.getId());
        if (status.equals("NEW"))
                status = scheduledHandler.handle(order.getId());

        order.setStatus(status);
        orderRepository.save(order);

        return OrderResponseDto.builder()
                .id(order.getId())
                .status(status)
                .build();
    }

    private Order createOrderAndTicket(OrderRequestDto orderRequestDto) {
        OrderTicket orderTicket = OrderTicket.builder()
                .id(orderRequestDto.getTicketId())
                .credentials(orderRequestDto.getCredentials())
                .build();

        Order order = Order.builder()
                .credentials(orderRequestDto.getCredentials())
                .status("NEW")
                .sum(orderRequestDto.getSum())
                .orderTicket(orderTicket)
                .build();

        orderTicketRepository.save(orderTicket);
        orderRepository.save(order);

        return order;
    }
}
