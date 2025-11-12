package com.cuellar.transactionservice.messaging;

import com.cuellar.transactionservice.event.UserCreatedEvent;
import com.cuellar.transactionservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class UserEventConsumers {

    private final AccountService accountService;

    @Bean
    public Consumer<UserCreatedEvent> userCreated() {
        return event -> accountService.ensureAccountByUuid(event.userUUID());
    }
}