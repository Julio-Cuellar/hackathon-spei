package com.cuellar.transactionservice.service;

import com.cuellar.transactionservice.domain.User;
import com.cuellar.transactionservice.dto.UserResponse;
import com.cuellar.transactionservice.dto.CreateUserRequest;
import com.cuellar.transactionservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserResponse createAccount(CreateUserRequest request) {
        User user = new User();
        user.setSaldo(request.getSaldoInicial() != null ? request.getSaldoInicial() : BigDecimal.ZERO);

        User saved = userRepository.save(user);
        return mapToResponse(saved);
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
        User user = userRepository.findByUuid(uuid)
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
        return mapToResponse(saved);
    }

    // Método auxiliar para validar si una cuenta puede enviar dinero
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
