package com.courier.tracking.notification.service;

import com.courier.tracking.common.events.ParcelDomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    // In-memory set for demo idempotency tracking
    private final Set<String> processedEvents = Collections.synchronizedSet(new HashSet<>());

    @RabbitListener(queues = "parcel.notification.queue")
    public void handleParcelEvent(ParcelDomainEvent event) {
        if (processedEvents.contains(event.getEventId())) {
            log.warn("Notification already dispatched for event {}. Skipping duplicate.", event.getEventId());
            return;
        }

        processedEvents.add(event.getEventId());

        switch (event.getEventType()) {
            case ShipmentCreated:
                log.info("🔔 [NOTIFICATION] SMS/Email to Customer: Your parcel {} has been registered!", event.getTrackingId());
                break;
            case ReachedHub:
                log.info("🔔 [NOTIFICATION] Alert: Parcel {} reached facility at {}", event.getTrackingId(), event.getLocation());
                break;
            case OutForDelivery:
                log.info("🔔 [NOTIFICATION] SMS: Parcel {} is OUT FOR DELIVERY today with {}!", event.getTrackingId(), event.getPerformedBy());
                break;
            case Delivered:
                log.info("🔔 [NOTIFICATION] SMS: Parcel {} has been DELIVERED. Thank you!", event.getTrackingId());
                break;
            default:
                log.info("🔔 [NOTIFICATION] Status update for parcel {}: {}", event.getTrackingId(), event.getStatusDetails());
                break;
        }
    }
}
