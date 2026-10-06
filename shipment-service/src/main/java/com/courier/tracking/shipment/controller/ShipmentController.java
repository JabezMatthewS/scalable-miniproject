package com.courier.tracking.shipment.controller;

import com.courier.tracking.shipment.dto.CreateShipmentRequest;
import com.courier.tracking.shipment.entity.Shipment;
import com.courier.tracking.shipment.service.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipments")
public class ShipmentController {
    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    public ResponseEntity<Shipment> createShipment(@Valid @RequestBody CreateShipmentRequest request) {
        return new ResponseEntity<>(shipmentService.createShipment(request), HttpStatus.CREATED);
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<Shipment> getShipment(@PathVariable String trackingId) {
        return ResponseEntity.ok(shipmentService.getShipmentByTrackingId(trackingId));
    }
}
