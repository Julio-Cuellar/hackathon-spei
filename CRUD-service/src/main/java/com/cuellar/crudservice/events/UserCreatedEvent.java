package com.cuellar.crudservice.events;

public record UserCreatedEvent(String userUUID, long occurredAt) {}
