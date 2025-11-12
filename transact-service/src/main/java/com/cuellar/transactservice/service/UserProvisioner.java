package com.cuellar.transactservice.service;


import com.cuellar.transactservice.domain.User;
import com.cuellar.transactservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserProvisioner {

    private final UserRepository userRepository;

    @Transactional
    public void ensureUser(String userUUID) {
        userRepository.findByUserUUID(userUUID).orElseGet(() -> {
            User u = new User();
            u.setUserUUID(userUUID);
            // saldo inicial en 0 (o ajusta si quieres otro default)
            u.setSaldo(BigDecimal.ZERO);
            u.setActiva(true);
            return userRepository.save(u);
        });
    }
}
