package com.cuellar.financialstruments.messaging.event;

public record CuentaClabeAddedEvent(
        String userUUID,
        String cuentaClabe,
        String bankName,
        String numeroCuenta,
        long occurredAt
) {}
