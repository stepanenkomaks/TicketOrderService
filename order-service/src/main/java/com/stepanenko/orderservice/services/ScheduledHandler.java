package com.stepanenko.orderservice.services;

import com.stepanenko.orderservice.services.interfaces.ScheduledHandlerInt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ScheduledHandler implements ScheduledHandlerInt {

    private final String TEMPORARY_ORDER_STATUS = "NEW";

    private String status;

    private final GetStatusService getStatusService;

    @Autowired
    public ScheduledHandler(GetStatusService getStatusService) {
        this.getStatusService = getStatusService;
    }


    public String handle(Long orderId) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        Runnable task1 = () -> {
            status = getStatusService.getStatus(orderId);
            log.info("Got status check: " + status);
        };

        ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(task1, 0, 1, TimeUnit.SECONDS);

        while (true) {
            if (!status.equals(TEMPORARY_ORDER_STATUS)) {
                scheduledFuture.cancel(true);
                ses.shutdown();
                return status;
            }
        }
    }
}
