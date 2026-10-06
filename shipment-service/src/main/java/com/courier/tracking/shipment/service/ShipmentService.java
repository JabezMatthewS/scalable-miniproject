package com.courier.tracking.shipment.service;

import com.courier.tracking.common.enums.ParcelEventType;
import com.courier.tracking.common.events.ParcelDomainEvent;
import com.courier.tracking.shipment.config.RabbitMQConfig;
import com.courier.tracking.shipment.dto.CreateShipmentRequest;
import com.courier.tracking.shipment.entity.Shipment;
import com.courier.tracking.shipment.repository.ShipmentRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final RabbitTemplate rabbitTemplate;

    public ShipmentService(ShipmentRepository shipmentRepository, RabbitTemplate rabbitTemplate) {
        this.shipmentRepository = shipmentRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public Shipment createShipment(CreateShipmentRequest request) {
        String trackingId = "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Shipment shipment = new Shipment(
                trackingId,
                request.getCustomerId(),
                request.getOrigin(),
                request.getDestination(),
                request.getWeight(),
                "CREATED",
                LocalDateTime.now()
        );

        Shipment savedShipment = shipmentRepository.save(shipment);

        // Publish domain event
        ParcelDomainEvent event = new ParcelDomainEvent(
                UUID.randomUUID().toString(),
                savedShipment.getTrackingId(),
                ParcelEventType.ShipmentCreated,
                savedShipment.getOrigin(),
                "Shipment created and ready for pickup",
                "Customer-" + savedShipment.getCustomerId(),
                LocalDateTime.now()
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY_SHIPMENT_CREATED,
                event
        );

        return savedShipment;
    }

    @Transactional(readOnly = true)
    public Shipment getShipmentByTrackingId(String trackingId) {
        return shipmentRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new RuntimeException("Shipment not found for tracking id: " + trackingId));
    }
}
