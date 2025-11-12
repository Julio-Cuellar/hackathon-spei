package com.cuellar.transactservice.event;

public record UserCreatedEvent(String userUUID, long occurredAt) {}
