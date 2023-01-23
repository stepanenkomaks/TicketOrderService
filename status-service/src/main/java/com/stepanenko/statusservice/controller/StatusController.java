package com.stepanenko.statusservice.controller;

import com.stepanenko.statusservice.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping()
    public ResponseEntity<String> getStatus(@RequestParam Long orderId) {
        return ResponseEntity.ok(statusService.getStatus(orderId));
    }
}
