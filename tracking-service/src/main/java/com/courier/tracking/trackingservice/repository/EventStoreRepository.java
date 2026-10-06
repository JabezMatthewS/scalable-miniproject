package com.courier.tracking.trackingservice.repository;

import com.courier.tracking.trackingservice.entity.StoredEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventStoreRepository extends JpaRepository<StoredEvent, Long> {
    List<StoredEvent> findByTrackingIdOrderByTimestampAsc(String trackingId);
    Optional<StoredEvent> findByEventId(String eventId);
}
