package com.cuellar.banksservice.events;

import java.time.Instant;

public record CuentaClabeAddedEvent(
        String userUUID,
        String cuentaClabe,
        String bankName,
        String numeroCuenta,
        long occurredAt
) {
    public CuentaClabeAddedEvent(String userUUID, String cuentaClabe, String bankName, String numeroCuenta) {
        this(userUUID, cuentaClabe, bankName, numeroCuenta, Instant.now().toEpochMilli());
    }
}
