package com.courier.tracking.trackingservice.service;

import com.courier.tracking.common.events.ParcelDomainEvent;
import com.courier.tracking.trackingservice.dto.TrackingHistoryResponse;
import com.courier.tracking.trackingservice.entity.StoredEvent;
import com.courier.tracking.trackingservice.repository.EventStoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrackingService {
    private static final Logger log = LoggerFactory.getLogger(TrackingService.class);
    private final EventStoreRepository eventStoreRepository;

    public TrackingService(EventStoreRepository eventStoreRepository) {
        this.eventStoreRepository = eventStoreRepository;
    }

    /**
     * Idempotent Event Sourcing Consumer:
     * Appends parcel domain events to the immutable event store.
     */
    @RabbitListener(queues = "parcel.tracking.events.queue")
    @Transactional
    public void consumeParcelEvent(ParcelDomainEvent event) {
        log.info("Received event {} for tracking ID {}", event.getEventType(), event.getTrackingId());

        // Idempotency check: Skip duplicate events
        if (eventStoreRepository.findByEventId(event.getEventId()).isPresent()) {
            log.warn("Duplicate event {} ignored (Idempotency check passed)", event.getEventId());
            return;
        }

        StoredEvent stored = new StoredEvent(
                event.getEventId(),
                event.getTrackingId(),
                event.getEventType(),
                event.getLocation(),
                event.getStatusDetails(),
                event.getPerformedBy(),
                event.getTimestamp()
        );

        eventStoreRepository.save(stored);
        log.info("Event successfully appended to Event Store for aggregate {}", event.getTrackingId());
    }

    /**
     * Event Sourcing Reconstruction:
     * Replays all recorded events in chronological order to compute current parcel state.
     */
    @Transactional(readOnly = true)
    public TrackingHistoryResponse reconstructParcelState(String trackingId) {
        List<StoredEvent> events = eventStoreRepository.findByTrackingIdOrderByTimestampAsc(trackingId);

        if (events.isEmpty()) {
            throw new RuntimeException("No tracking history found for ID: " + trackingId);
        }

        StoredEvent latestEvent = events.get(events.size() - 1);

        return new TrackingHistoryResponse(
                trackingId,
                latestEvent.getEventType().name(),
                latestEvent.getLocation(),
                latestEvent.getTimestamp(),
                events
        );
    }
}
