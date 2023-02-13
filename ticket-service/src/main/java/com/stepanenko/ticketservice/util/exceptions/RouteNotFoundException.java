package com.stepanenko.ticketservice.util.exceptions;

public class RouteNotFoundException extends RuntimeException{
    public RouteNotFoundException(String message) {
        super(message);
    }
}
