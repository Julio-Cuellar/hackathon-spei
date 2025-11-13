package com.cuellar.financialstruments.messaging;

import com.cuellar.financialstruments.messaging.event.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;

@Configuration
@Slf4j
public class UserEventConsumers {

    @Bean
    public Consumer<UserCreatedEvent> userCreated() {
        return event -> {
            log.info("📨 Usuario registrado: {}", event.userUUID());
        };
    }
}