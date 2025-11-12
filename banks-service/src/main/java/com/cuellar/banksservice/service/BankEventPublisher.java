package com.cuellar.banksservice.service;

import com.cuellar.banksservice.events.CuentaClabeAddedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankEventPublisher {

    private final StreamBridge streamBridge;

    public void publishCuentaClabeAdded(String userUUID, String cuentaClabe, String bankName, String numeroCuenta) {
        CuentaClabeAddedEvent event = new CuentaClabeAddedEvent(userUUID, cuentaClabe, bankName, numeroCuenta);

        boolean sent = streamBridge.send("cuentaClabeAdded-out-0", event);

        if (sent) {
            log.info("Evento CuentaClabeAdded publicado: userUUID={}, cuentaClabe={}", userUUID, cuentaClabe);
        } else {
            log.error("Error al publicar evento CuentaClabeAdded para userUUID={}", userUUID);
        }
    }
}
