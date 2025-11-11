package com.cuellar.authservice.events;

public record UserCreatedEvent(String userUUID, long occurredAt) {}
