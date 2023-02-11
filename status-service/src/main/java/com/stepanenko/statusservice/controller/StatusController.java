package com.stepanenko.statusservice.controller;

import com.stepanenko.statusservice.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
@Tag(name = "Status", description = "The status controller")
public class StatusController {

    private final StatusService statusService;

    @Operation(summary = "Get status for provided order id")
    @GetMapping()
    public ResponseEntity<String> getStatus(@RequestParam @Parameter(example = "1") Long orderId) {
        return ResponseEntity.ok(statusService.getStatus(orderId));
    }
}
