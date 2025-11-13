package com.brokerservice.brocker_service.messaging.event;

public record UserCreatedEvent(
        String userUUID,
        long occurredAt
) {}
