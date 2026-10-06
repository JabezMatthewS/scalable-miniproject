package com.courier.tracking.shipment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateShipmentRequest {
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be greater than zero")
    private Double weight;

    public CreateShipmentRequest() {}

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
}
