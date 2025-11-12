package com.cuellar.transactionservice.event;

public record UserCreatedEvent(String userUUID, long occurredAt) {}
