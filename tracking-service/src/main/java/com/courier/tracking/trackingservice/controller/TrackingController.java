package com.courier.tracking.trackingservice.controller;

import com.courier.tracking.trackingservice.dto.TrackingHistoryResponse;
import com.courier.tracking.trackingservice.service.TrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tracking")
public class TrackingController {
    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackingHistoryResponse> getTrackingHistory(@PathVariable("trackingId") String trackingId) {
        return ResponseEntity.ok(trackingService.reconstructParcelState(trackingId));
    }
}
