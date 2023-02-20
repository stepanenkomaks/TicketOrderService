package com.stepanenko.orderservice.services;

import com.stepanenko.orderservice.client.GetStatusClient;
import com.stepanenko.orderservice.services.interfaces.ScheduledHandlerInt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledHandler implements ScheduledHandlerInt {

    private static final String TEMPORARY_ORDER_STATUS = "NEW";
    private static final String FAIL_ORDER_STATUS = "FAIL";

    private String status;

    private final GetStatusClient getStatusClient;

    //Method checks status every second.
    //If after 1 minute status was not received it makes status "FAIL"
    public String handle(Long orderId) {
        status = TEMPORARY_ORDER_STATUS;

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable checkStatusTask = () -> {
            status = getStatusClient.getStatus(orderId);
            log.info("Got status check: " + status);
        };

        ScheduledFuture<?> statusHandler = scheduler.scheduleAtFixedRate(checkStatusTask, 0, 1, TimeUnit.SECONDS);

        //If status was not updated in 1 minute - statusHandler will be stopped
        Runnable stopStatusCheckTask = () -> {
            statusHandler.cancel(true);
            status = FAIL_ORDER_STATUS;
        };

        ScheduledFuture<?> stopCheckHandler = scheduler.schedule(stopStatusCheckTask, 1, TimeUnit.MINUTES);

        while (true) {
            if (!stopCheckHandler.isDone()) {
                if (!status.equals(TEMPORARY_ORDER_STATUS)) {
                    statusHandler.cancel(true);
                    stopCheckHandler.cancel(true);
                    scheduler.shutdown();
                    return status;
                }
            } else {
                scheduler.shutdown();
                return status;
            }
        }
    }
}
