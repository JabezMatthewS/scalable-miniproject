package com.courier.tracking.hub.controller;

import com.courier.tracking.hub.dto.DeliveryStatusUpdateRequest;
import com.courier.tracking.hub.dto.HubScanRequest;
import com.courier.tracking.hub.dto.ParcelCollectRequest;
import com.courier.tracking.hub.service.HubDeliveryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HubDeliveryController {
    private final HubDeliveryService hubDeliveryService;

    public HubDeliveryController(HubDeliveryService hubDeliveryService) {
        this.hubDeliveryService = hubDeliveryService;
    }

    @PostMapping("/deliveries/collect")
    public ResponseEntity<Map<String, String>> collectParcel(@Valid @RequestBody ParcelCollectRequest request) {
        hubDeliveryService.recordParcelCollected(
                request.getTrackingId(),
                request.getAgentName() != null ? request.getAgentName() : "Courier Bob",
                request.getPickupLocation() != null ? request.getPickupLocation() : "Origin Location"
        );
        return ResponseEntity.ok(Map.of("message", "Parcel collected event published successfully"));
    }

    @PostMapping("/hubs/scan-in")
    public ResponseEntity<Map<String, String>> scanInHub(@Valid @RequestBody HubScanRequest request) {
        hubDeliveryService.recordReachedHub(request);
        return ResponseEntity.ok(Map.of("message", "ReachedHub event published successfully"));
    }

    @PostMapping("/hubs/scan-out")
    public ResponseEntity<Map<String, String>> scanOutHub(@Valid @RequestBody HubScanRequest request) {
        hubDeliveryService.recordLeftHub(request);
        return ResponseEntity.ok(Map.of("message", "LeftHub event published successfully"));
    }

    @PostMapping("/deliveries/out-for-delivery")
    public ResponseEntity<Map<String, String>> outForDelivery(@Valid @RequestBody DeliveryStatusUpdateRequest request) {
        hubDeliveryService.recordOutForDelivery(request);
        return ResponseEntity.ok(Map.of("message", "OutForDelivery event published successfully"));
    }

    @PostMapping("/deliveries/complete")
    public ResponseEntity<Map<String, String>> completeDelivery(@Valid @RequestBody DeliveryStatusUpdateRequest request) {
        hubDeliveryService.recordDelivered(request);
        return ResponseEntity.ok(Map.of("message", "Delivered event published successfully"));
    }
}
