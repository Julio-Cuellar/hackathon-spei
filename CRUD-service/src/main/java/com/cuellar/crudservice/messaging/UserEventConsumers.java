package com.cuellar.crudservice.messaging;

import com.cuellar.crudservice.events.UserCreatedEvent;
import com.cuellar.crudservice.service.UserProvisioner;
import com.cuellar.crudservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class UserEventConsumers {

    private final UserService userService;

    @Bean
    public Consumer<UserCreatedEvent> userCreated() {
        return event -> userService.ensureUser(event.userUUID());
    }
}