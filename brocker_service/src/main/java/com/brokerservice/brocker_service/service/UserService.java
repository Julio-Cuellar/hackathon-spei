package com.brokerservice.brocker_service.service;

import com.brokerservice.brocker_service.domain.User;
import com.brokerservice.brocker_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser() {
        User user = User.builder()
                .balance(BigDecimal.valueOf(10000.00))
                .build();

        return userRepository.save(user);
    }

    // ✅ CORREGIDO: Cambié UUID por Long
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));
    }

    // ✅ CORREGIDO: Cambié UUID por Long
    @Transactional
    public User updateUserBalance(Long userId, BigDecimal newBalance) {
        User user = getUserById(userId);
        user.setBalance(newBalance);
        return userRepository.save(user);
    }

    // ✅ MÉTODO NUEVO: Para buscar por UUID string
    @Transactional(readOnly = true)
    public User getUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con UUID: " + uuid));
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}