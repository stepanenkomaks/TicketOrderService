package com.stepanenko.orderservice.util.exceptions;

public class StatusNotReceived extends RuntimeException{
    public StatusNotReceived(String message) {
        super(message);
    }
}
