package com.courier.tracking.hub.service;

import com.courier.tracking.common.enums.ParcelEventType;
import com.courier.tracking.common.events.ParcelDomainEvent;
import com.courier.tracking.hub.config.RabbitMQHubConfig;
import com.courier.tracking.hub.dto.DeliveryStatusUpdateRequest;
import com.courier.tracking.hub.dto.HubScanRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class HubDeliveryService {
    private final RabbitTemplate rabbitTemplate;

    public HubDeliveryService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void recordParcelCollected(String trackingId, String agentName, String pickupLocation) {
        publishEvent(trackingId, ParcelEventType.ParcelCollected, pickupLocation, "Parcel collected from sender", agentName, "parcel.collected");
    }

    public void recordReachedHub(HubScanRequest request) {
        publishEvent(request.getTrackingId(), ParcelEventType.ReachedHub, request.getHubLocation(), "Parcel arrived at hub: " + request.getHubId(), "HubOperator-" + request.getHubId(), "parcel.reached.hub");
    }

    public void recordLeftHub(HubScanRequest request) {
        publishEvent(request.getTrackingId(), ParcelEventType.LeftHub, request.getHubLocation(), "Parcel departed from hub: " + request.getHubId(), "HubOperator-" + request.getHubId(), "parcel.left.hub");
    }

    public void recordOutForDelivery(DeliveryStatusUpdateRequest request) {
        publishEvent(request.getTrackingId(), ParcelEventType.OutForDelivery, request.getCurrentArea(), "Parcel is out for delivery with agent: " + request.getAgentName(), request.getAgentName(), "parcel.out.for.delivery");
    }

    public void recordDelivered(DeliveryStatusUpdateRequest request) {
        publishEvent(request.getTrackingId(), ParcelEventType.Delivered, request.getCurrentArea(), "Parcel delivered successfully. Signed by: " + request.getRecipientSignature(), request.getAgentName(), "parcel.delivered");
    }

    private void publishEvent(String trackingId, ParcelEventType eventType, String location, String details, String performedBy, String routingKey) {
        ParcelDomainEvent event = new ParcelDomainEvent(
                UUID.randomUUID().toString(),
                trackingId,
                eventType,
                location != null ? location : "In Transit",
                details,
                performedBy,
                LocalDateTime.now()
        );
        rabbitTemplate.convertAndSend(RabbitMQHubConfig.EXCHANGE_NAME, routingKey, event);
    }
}
