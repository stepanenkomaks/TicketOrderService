package com.stepanenko.statusservice.service;

import com.stepanenko.statusservice.model.Status;
import com.stepanenko.statusservice.service.interfaces.StatusServiceInt;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@NoArgsConstructor
public class StatusService implements StatusServiceInt {

    public String getStatus(Long orderId) {
        List<String> statuses = Stream.of(Status.values()).map(Enum::toString).toList();
        Random random = new Random();
        return statuses.get(random.nextInt(statuses.size()));
    }
}
