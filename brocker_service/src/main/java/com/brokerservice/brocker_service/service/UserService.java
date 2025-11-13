package com.brokerservice.brocker_service.service;

import com.brokerservice.brocker_service.domain.User;
import com.brokerservice.brocker_service.dto.UserResponseDTO;
import com.brokerservice.brocker_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDTO createUser(String userUUID, BigDecimal initialBalance) {
        log.info("Creando usuario con UUID recibido: {}", userUUID);
        validateUUID(userUUID);

        if (userRepository.existsById(userUUID)) {
            log.error("Usuario ya existe con UUID: {}", userUUID);
            throw new IllegalArgumentException("Ya existe un usuario con UUID: " + userUUID);
        }

        User user = User.builder()
                .userUUID(userUUID)
                .balance(initialBalance)
                .build();

        User savedUser = userRepository.save(user);
        log.info("Usuario creado exitosamente con UUID: {}", savedUser.getUserUUID());

        return mapToResponseDTO(savedUser);
    }

    @Transactional
    public UserResponseDTO createUser(String userUUID) {
        return createUser(userUUID, BigDecimal.valueOf(10000.00));
    }

    @Transactional
    public void registerUserFromEvent(String userUUID) {
        if (userRepository.existsById(userUUID)) {
            log.warn("⚠️ Usuario {} ya existe", userUUID);
            return;
        }

        User user = User.builder()
                .userUUID(userUUID)
                .balance(BigDecimal.valueOf(10000.00))
                .build();

        userRepository.save(user);
        log.info("💾 Usuario {} guardado en BD con $10,000", userUUID);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserByUUID(String userUUID) {
        log.debug("Buscando usuario con UUID: {}", userUUID);
        User user = findUserEntityByUUID(userUUID);
        return mapToResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        log.debug("Obteniendo todos los usuarios");
        return userRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public User findUserEntityByUUID(String userUUID) {
        return userRepository.findById(userUUID)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con UUID: {}", userUUID);
                    return new IllegalArgumentException("Usuario no encontrado con UUID: " + userUUID);
                });
    }

    @Transactional
    public void updateUserBalance(String userUUID, BigDecimal newBalance) {
        log.debug("💰 Actualizando balance del usuario {} a {}", userUUID, newBalance);
        User user = findUserEntityByUUID(userUUID);
        user.setBalance(newBalance);
        userRepository.save(user);
        log.info("Balance actualizado correctamente para UUID: {}", userUUID);
    }

    @Transactional
    public void deleteUser(String userUUID) {
        log.info("Eliminando usuario con UUID: {}", userUUID);
        if (!userRepository.existsById(userUUID)) {
            throw new IllegalArgumentException("Usuario no encontrado con UUID: " + userUUID);
        }
        userRepository.deleteById(userUUID);
        log.info("Usuario eliminado: {}", userUUID);
    }

    private void validateUUID(String uuid) {
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            log.error("UUID inválido: {}", uuid);
            throw new IllegalArgumentException("UUID inválido: " + uuid);
        }
    }

    private UserResponseDTO mapToResponseDTO(User user) {
        return UserResponseDTO.builder()
                .uuid(user.getUserUUID())
                .balance(user.getBalance())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}