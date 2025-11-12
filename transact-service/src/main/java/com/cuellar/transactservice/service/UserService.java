package com.cuellar.transactservice.service;

import com.cuellar.transactservice.domain.User;
import com.cuellar.transactservice.dto.CreateUserRequest;
import com.cuellar.transactservice.dto.UserResponse;
import com.cuellar.transactservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse createAccount(CreateUserRequest request) {
        User user = new User();
        user.setSaldo(request.getSaldoInicial() != null ? request.getSaldoInicial() : BigDecimal.ZERO);
        user.setActiva(true);
        User saved = userRepository.save(user);
        log.info("Cuenta creada manualmente id={}, UUID={}", saved.getId(), saved.getUserUUID());
        return mapToResponse(saved);
    }

    // Idempotente para el consumidor de eventos y reutilizable internamente
    @Transactional
    public User ensureUser(String userUUID) {
        return userRepository.findByUserUUID(userUUID)
                .map(existing -> {
                    log.debug("Usuario ya existe, UUID={}", userUUID);
                    return existing;
                })
                .orElseGet(() -> {
                    log.info("Provisionando usuario por evento, UUID={}", userUUID);
                    User u = new User();
                    u.setUserUUID(userUUID);
                    u.setSaldo(BigDecimal.ZERO);
                    u.setActiva(true);
                    User saved = userRepository.save(u);
                    log.info("Usuario creado, id={}, UUID={}", saved.getId(), userUUID);
                    return saved;
                });
    }

    public List<UserResponse> getAllAccounts() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getAccountById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id));
        return mapToResponse(user);
    }

    public UserResponse getAccountByUuid(String uuid) {
        User user = userRepository.findByUserUUID(uuid)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con UUID: " + uuid));
        return mapToResponse(user);
    }

    public UserResponse getAccountByCuentaClave(String cuentaClave) {
        User user = userRepository.findByCuentaClave(cuentaClave)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con CLABE: " + cuentaClave));
        return mapToResponse(user);
    }

    @Transactional
    public UserResponse toggleAccountStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id));
        user.setActiva(!user.getActiva());
        User saved = userRepository.save(user);
        log.info("Cuenta {} para id={}", saved.getActiva() ? "activada" : "desactivada", id);
        return mapToResponse(saved);
    }

    public void validateAccountCanSend(User user) {
        if (!user.getActiva()) {
            throw new RuntimeException("La cuenta está deshabilitada y no puede enviar dinero. Solo puede recibir.");
        }
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUuid(user.getUserUUID());
        response.setCuentaClave(user.getCuentaClave());
        response.setSaldo(user.getSaldo());
        response.setFechaCreacion(user.getFechaCreacion());
        response.setActiva(user.getActiva());
        return response;
    }
}
