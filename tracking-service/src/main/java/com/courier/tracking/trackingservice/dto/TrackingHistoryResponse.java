package com.courier.tracking.trackingservice.dto;

import com.courier.tracking.trackingservice.entity.StoredEvent;
import java.time.LocalDateTime;
import java.util.List;

public class TrackingHistoryResponse {
    private String trackingId;
    private String currentStatus;
    private String lastLocation;
    private LocalDateTime lastUpdated;
    private List<StoredEvent> auditHistory;

    public TrackingHistoryResponse() {}

    public TrackingHistoryResponse(String trackingId, String currentStatus, String lastLocation, LocalDateTime lastUpdated, List<StoredEvent> auditHistory) {
        this.trackingId = trackingId;
        this.currentStatus = currentStatus;
        this.lastLocation = lastLocation;
        this.lastUpdated = lastUpdated;
        this.auditHistory = auditHistory;
    }

    public String getTrackingId() { return trackingId; }
    public void setTrackingId(String trackingId) { this.trackingId = trackingId; }

    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }

    public String getLastLocation() { return lastLocation; }
    public void setLastLocation(String lastLocation) { this.lastLocation = lastLocation; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    public List<StoredEvent> getAuditHistory() { return auditHistory; }
    public void setAuditHistory(List<StoredEvent> auditHistory) { this.auditHistory = auditHistory; }
}
