package com.stepanenko.orderservice.event;

public record OrderEvent(Long orderId, String status) {
}
