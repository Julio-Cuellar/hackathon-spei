package com.cuellar.financialstruments.messaging;

import com.cuellar.financialstruments.messaging.event.CuentaClabeAddedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CuentaClabeConsumers {

    @Bean
    public Consumer<CuentaClabeAddedEvent> cuentaClabeAdded() {
        return event -> {
            log.info("📨 Recibido CuentaClabeAdded: userUUID={}, clabe={}, bank={}",
                    event.userUUID(),
                    event.cuentaClabe(),
                    event.bankName());
            log.info(" Cuenta CLABE registrada. El usuario puede solicitar instrumentos financieros.");

        };
    }
}
