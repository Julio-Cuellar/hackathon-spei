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
    public UserResponseDTO createUser() {
        log.info(" Creando nuevo usuario");

        User user = User.builder()
                .uuid(UUID.randomUUID().toString())
                .balance(BigDecimal.valueOf(10000.00))
                .build();

        User savedUser = userRepository.save(user);
        log.info("Usuario creado con ID: {} y UUID: {}", savedUser.getId(), savedUser.getUuid());

        return mapToResponseDTO(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long userId) {
        log.debug("Buscando usuario con ID: {}", userId);
        User user = findUserEntityById(userId);
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
    public User findUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error(" Usuario no encontrado con ID: {}", userId);
                    return new IllegalArgumentException("Usuario no encontrado con ID: " + userId);
                });
    }

    @Transactional
    public void updateUserBalance(Long userId, BigDecimal newBalance) {
        log.debug("Actualizando balance del usuario {} a {}", userId, newBalance);
        User user = findUserEntityById(userId);
        user.setBalance(newBalance);
        userRepository.save(user);
        log.info(" Balance actualizado correctamente");
    }


    private UserResponseDTO mapToResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .uuid(user.getUuid())
                .balance(user.getBalance())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
