package com.cuellar.financialstruments.messaging.event;

public record UserCreatedEvent(
        String userUUID,
        long occurredAt
) {}
