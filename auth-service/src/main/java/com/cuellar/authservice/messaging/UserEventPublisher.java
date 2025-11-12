package com.cuellar.authservice.messaging;

import com.cuellar.authservice.events.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventPublisher {

    private final StreamBridge streamBridge;

    public boolean publishUserCreated(String userUUID) {
        UserCreatedEvent event = new UserCreatedEvent(userUUID, System.currentTimeMillis());
        Message<UserCreatedEvent> msg = MessageBuilder.withPayload(event)
                // headers opcionales para routing adicional
                .setHeaderIfAbsent("type", "UserCreated")
                .build();
        // binding name = <bindingId> tal cual en properties
        return streamBridge.send("userCreated-out-0", msg);
    }
}