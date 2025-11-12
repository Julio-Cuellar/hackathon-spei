package com.cuellar.transactservice.service;

import com.cuellar.transactservice.config.RabbitConfig;
import com.cuellar.transactservice.domain.User;
import com.cuellar.transactservice.event.UserCreatedEvent;
import com.cuellar.transactservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCreatedHandler {

    private final UserRepository userRepository;

    @Transactional
    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void onUserCreated(UserCreatedEvent event) {
        String userUUID = event.getData().getUserUUID();
        userRepository.findByUserUUID(userUUID).ifPresent(u -> { /* ya existe, idempotente */ return; }); // [web:10][web:13]

        User newUser = User.builder()
                .userUUID(userUUID) // del evento (clave idempotente) [web:10]
                .build();

        userRepository.save(newUser); // @PrePersist rellena el resto [web:14]
    }
}