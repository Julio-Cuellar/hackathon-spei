package com.cuellar.banksservice.events;

public record UserCreatedEvent(String userUUID, long occurredAt) {}
