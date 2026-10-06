package com.courier.tracking.hub.dto;

import jakarta.validation.constraints.NotBlank;

public class DeliveryStatusUpdateRequest {
    @NotBlank(message = "Tracking ID is required")
    private String trackingId;

    @NotBlank(message = "Agent ID / Name is required")
    private String agentName;

    private String currentArea;
    private String recipientSignature;

    public DeliveryStatusUpdateRequest() {}

    public String getTrackingId() { return trackingId; }
    public void setTrackingId(String trackingId) { this.trackingId = trackingId; }

    public String getAgentName() { return agentName; }
    public void setAgentName(String agentName) { this.agentName = agentName; }

    public String getCurrentArea() { return currentArea; }
    public void setCurrentArea(String currentArea) { this.currentArea = currentArea; }

    public String getRecipientSignature() { return recipientSignature; }
    public void setRecipientSignature(String recipientSignature) { this.recipientSignature = recipientSignature; }
}
