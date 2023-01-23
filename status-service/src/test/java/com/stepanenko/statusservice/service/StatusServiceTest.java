package com.stepanenko.statusservice.service;

import com.stepanenko.statusservice.model.Status;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StatusServiceTest {

    private final StatusService statusService = new StatusService();

    @Test
    void shouldGetStatus() {
        //GIVEN
        List<String> statuses = Stream.of(Status.values()).map(Enum::toString).toList();

        //WHEN
        String response = statusService.getStatus(1L);

        //THEN
        assertTrue(statuses.contains(response));
    }
}