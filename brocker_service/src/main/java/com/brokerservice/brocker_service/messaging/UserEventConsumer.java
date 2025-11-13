package com.brokerservice.brocker_service.messaging;

import com.brokerservice.brocker_service.messaging.event.UserCreatedEvent;
import com.brokerservice.brocker_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class UserEventConsumer {

    private final UserService userService;

    @Bean
    public Consumer<UserCreatedEvent> userCreated() {
        return event -> {
            log.info("📨 Recibido UserCreatedEvent: {}", event.userUUID());
            userService.registerUserFromEvent(event.userUUID());
            log.info("✅ Usuario guardado en BD");
        };
    }
}