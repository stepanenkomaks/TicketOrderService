package com.example.notificationservice.event;

public record OrderEvent(Long orderId, String status) {
}
