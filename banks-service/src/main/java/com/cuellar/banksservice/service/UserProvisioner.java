package com.cuellar.banksservice.service;

import com.cuellar.banksservice.domain.User;
import com.cuellar.banksservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProvisioner {

    private final UserRepository userRepository;

    @Transactional
    public void ensureUser(String userUUID) {
        userRepository.findByUUID(userUUID).orElseGet(() -> {
            log.info("Provisionando usuario por evento, UUID: {}", userUUID);
            User u = User.builder()
                    .UUID(userUUID)
                    .name(null)           // Se llenará después
                    .lastName(null)       // Se llenará después
                    .bankName(null)       // Se llenará después
                    .numeroCuenta(null)   // Se llenará después
                    .cuentaClabe(null)    // Se llenará después
                    .monto("0.00")        // Valor inicial por defecto
                    .build();
            return userRepository.save(u);
        });
    }
}
