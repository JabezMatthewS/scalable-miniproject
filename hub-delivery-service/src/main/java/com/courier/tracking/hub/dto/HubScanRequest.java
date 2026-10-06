package com.courier.tracking.hub.dto;

import jakarta.validation.constraints.NotBlank;

public class HubScanRequest {
    @NotBlank(message = "Tracking ID is required")
    private String trackingId;

    @NotBlank(message = "Hub ID / Code is required")
    private String hubId;

    @NotBlank(message = "Hub Location is required")
    private String hubLocation;

    private String notes;

    public HubScanRequest() {}

    public String getTrackingId() { return trackingId; }
    public void setTrackingId(String trackingId) { this.trackingId = trackingId; }

    public String getHubId() { return hubId; }
    public void setHubId(String hubId) { this.hubId = hubId; }

    public String getHubLocation() { return hubLocation; }
    public void setHubLocation(String hubLocation) { this.hubLocation = hubLocation; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
