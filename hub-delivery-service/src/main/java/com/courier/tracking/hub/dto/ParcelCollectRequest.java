package com.courier.tracking.hub.dto;

public class ParcelCollectRequest {
    private String trackingId;
    private String agentName;
    private String pickupLocation;

    public ParcelCollectRequest() {}

    public ParcelCollectRequest(String trackingId, String agentName, String pickupLocation) {
        this.trackingId = trackingId;
        this.agentName = agentName;
        this.pickupLocation = pickupLocation;
    }

    public String getTrackingId() { return trackingId; }
    public void setTrackingId(String trackingId) { this.trackingId = trackingId; }

    public String getAgentName() { return agentName; }
    public void setAgentName(String agentName) { this.agentName = agentName; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
}
