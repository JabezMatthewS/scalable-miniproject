package com.courier.tracking.trackingservice.entity;

import com.courier.tracking.common.enums.ParcelEventType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_store", indexes = {
    @Index(name = "idx_tracking_id", columnList = "trackingId"),
    @Index(name = "idx_timestamp", columnList = "timestamp")
})
public class StoredEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String eventId;

    @Column(nullable = false)
    private String trackingId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParcelEventType eventType;

    private String location;
    private String statusDetails;
    private String performedBy;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public StoredEvent() {}

    public StoredEvent(String eventId, String trackingId, ParcelEventType eventType, String location, String statusDetails, String performedBy, LocalDateTime timestamp) {
        this.eventId = eventId;
        this.trackingId = trackingId;
        this.eventType = eventType;
        this.location = location;
        this.statusDetails = statusDetails;
        this.performedBy = performedBy;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
