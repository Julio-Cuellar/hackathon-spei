package com.cuellar.transactservice.messaging;

import com.cuellar.transactservice.event.UserCreatedEvent;
import com.cuellar.transactservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreatedListener {

    private final UserService userService;

    @RabbitListener(queues = com.cuellar.transactservice.config.RabbitConfig.QUEUE)
    public void onUserCreated(@Payload UserCreatedEvent event) {
        if (event == null || event.getData() == null || event.getData().getUserUUID() == null) {
            log.warn("Evento user.created inválido o sin userUUID");
            return;
        }
        String userUUID = event.getData().getUserUUID();
        userService.ensureUser(userUUID);
        log.info("Procesado user.created para userUUID={}", userUUID);
    }
}
