package com.stepanenko.util.exceptions;

public class StatusNotReceived extends RuntimeException{
    public StatusNotReceived(String message) {
        super(message);
    }
}
