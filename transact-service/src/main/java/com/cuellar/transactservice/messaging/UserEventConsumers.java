package com.cuellar.transactservice.messaging;

import com.cuellar.transactservice.event.UserCreatedEvent;
import com.cuellar.transactservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class UserEventConsumers {

    private final UserService userService;

    @Bean
    public Consumer<UserCreatedEvent> userCreated() {
        return event -> {
            log.info("Recibido UserCreated userUUID={}, occurredAt={}", event.userUUID(), event.occurredAt());
            userService.ensureUser(event.userUUID());
            log.debug("Procesado UserCreated para UUID={}", event.userUUID());
        };
    }
}
