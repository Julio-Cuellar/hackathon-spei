package com.cuellar.transactservice.event;

public record CuentaClabeAddedEvent(
        String userUUID,
        String cuentaClabe,
        String bankName,
        String numeroCuenta,
        long occurredAt
) {}
