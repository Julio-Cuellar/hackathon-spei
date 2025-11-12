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

    // Nuevo método para actualizar cuenta CLABE desde evento
    @Transactional
    public void updateCuentaClabe(String userUUID, String cuentaClabe, String bankName) {
        User user = userRepository.findByUserUUID(userUUID)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado para UUID: {}", userUUID);
                    return new RuntimeException("User not found: " + userUUID);
                });

        // Solo actualizar si los valores han cambiado (idempotencia)
        boolean updated = false;

        if (cuentaClabe != null && !cuentaClabe.equals(user.getCuentaClabe())) {
            user.setCuentaClabe(cuentaClabe);
            updated = true;
        }

        if (updated) {
            userRepository.save(user);
            log.info("Cuenta CLABE actualizada para usuario UUID={}: CLABE={}",
                    userUUID, cuentaClabe);
        } else {
            log.debug("Cuenta CLABE sin cambios para usuario UUID={}", userUUID);
        }
    }
}
