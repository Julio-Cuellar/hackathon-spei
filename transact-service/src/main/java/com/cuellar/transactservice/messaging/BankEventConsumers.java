package com.cuellar.transactservice.messaging;

import com.cuellar.transactservice.event.CuentaClabeAddedEvent;
import com.cuellar.transactservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BankEventConsumers {

    private final UserService userService;

    @Bean
    public Consumer<CuentaClabeAddedEvent> cuentaClabeAdded() {
        return event -> {
            log.info("Recibido CuentaClabeAdded userUUID={}, cuentaClabe={}",
                    event.userUUID(), event.cuentaClabe());

            userService.updateCuentaClabe(
                    event.userUUID(),
                    event.cuentaClabe(),
                    event.bankName()
            );

            log.debug("Procesado CuentaClabeAdded para UUID={}", event.userUUID());
        };
    }
}
