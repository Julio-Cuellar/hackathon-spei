package com.cuellar.transactservice.service;

import com.cuellar.transactservice.domain.User;
import com.cuellar.transactservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    // Solo lectura: usado por endpoints GET si necesitas saber si existe
    @Transactional(readOnly = true)
    public Optional<User> findByUUID(String userUUID) {
        return userRepository.findByUserUUID(userUUID);
    }

    // Escritura: usado por POST/PUT/DELETE que necesitan garantizar existencia
    @Transactional
    public User getOrCreateUser(String userUUID) {
        return userRepository.findByUserUUID(userUUID)
                .orElseGet(() -> {
                    log.info("Usuario no encontrado. Creando nuevo usuario con UUID: {}", userUUID);
                    User newUser = new User();
                    newUser.setUserUUID(userUUID);
                    return userRepository.save(newUser);
                });
    }

    // Idempotente para el consumidor de eventos (UserCreated)
    @Transactional
    public void ensureUser(String userUUID) {
        userRepository.findByUserUUID(userUUID)
                .orElseGet(() -> {
                    log.info("Provisionando usuario por evento, UUID: {}", userUUID);
                    User newUser = new User();
                    newUser.setUserUUID(userUUID);
                    return userRepository.save(newUser);
                });
    }
}
