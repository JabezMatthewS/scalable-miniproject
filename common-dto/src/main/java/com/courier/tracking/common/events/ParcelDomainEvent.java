package com.courier.tracking.common.events;

import com.courier.tracking.common.enums.ParcelEventType;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ParcelDomainEvent implements Serializable {
    private String eventId;
    private String trackingId;
    private ParcelEventType eventType;
    private String location;
    private String statusDetails;
    private String performedBy;
    private LocalDateTime timestamp;

    public ParcelDomainEvent() {}

    public ParcelDomainEvent(String eventId, String trackingId, ParcelEventType eventType, String location, String statusDetails, String performedBy, LocalDateTime timestamp) {
        this.eventId = eventId;
        this.trackingId = trackingId;
        this.eventType = eventType;
        this.location = location;
        this.statusDetails = statusDetails;
        this.performedBy = performedBy;
        this.timestamp = timestamp;
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getTrackingId() { return trackingId; }
    public void setTrackingId(String trackingId) { this.trackingId = trackingId; }

    public ParcelEventType getEventType() { return eventType; }
    public void setEventType(ParcelEventType eventType) { this.eventType = eventType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getStatusDetails() { return statusDetails; }
    public void setStatusDetails(String statusDetails) { this.statusDetails = statusDetails; }

    public String getPerformedBy() { return performedBy; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
