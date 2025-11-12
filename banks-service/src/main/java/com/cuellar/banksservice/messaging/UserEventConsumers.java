package com.cuellar.banksservice.messaging;

import com.cuellar.banksservice.events.UserCreatedEvent;
import com.cuellar.banksservice.service.UserProvisioner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class UserEventConsumers {

    private final UserProvisioner userProvisioner;

    @Bean
    public Consumer<UserCreatedEvent> userCreated() {
        return event -> {
            log.info("Recibido UserCreated userUUID={}, occurredAt={}",
                    event.userUUID(), event.occurredAt());
            userProvisioner.ensureUser(event.userUUID());
            log.debug("Procesado UserCreated para UUID={}", event.userUUID());
        };
    }
}
