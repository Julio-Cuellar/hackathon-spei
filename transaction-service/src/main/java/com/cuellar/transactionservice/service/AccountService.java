package com.cuellar.transactionservice.service;

import com.cuellar.transactionservice.domain.User;
import com.cuellar.transactionservice.dto.AccountResponse;
import com.cuellar.transactionservice.dto.CreateAccountRequest;
import com.cuellar.transactionservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // Idempotente: provisiona una cuenta/usuario por UUID cuando llegue el evento
    @Transactional
    public void ensureAccountByUuid(String uuid) {
        accountRepository.findByUuid(uuid).orElseGet(() -> {
            User user = new User();
            user.setUuid(uuid);
            user.setSaldo(BigDecimal.ZERO);
            // setea otros defaults si aplica (activa=true, generar cuentaClave, etc.)
            // p.ej. user.setActiva(Boolean.TRUE);
            return accountRepository.save(user);
        });
    }

    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        User user = new User();
        user.setSaldo(request.getSaldoInicial() != null ? request.getSaldoInicial() : BigDecimal.ZERO);
        // si CreateAccountRequest lleva uuid/cuentaClave, setéalos aquí
        User saved = accountRepository.save(user);
        return mapToResponse(saved);
    }

    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AccountResponse getAccountById(Long id) {
        User user = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id));
        return mapToResponse(user);
    }

    public AccountResponse getAccountByUuid(String uuid) {
        User user = accountRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con UUID: " + uuid));
        return mapToResponse(user);
    }

    public AccountResponse getAccountByCuentaClave(String cuentaClave) {
        User user = accountRepository.findByCuentaClave(cuentaClave)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con CLABE: " + cuentaClave));
        return mapToResponse(user);
    }

    @Transactional
    public AccountResponse toggleAccountStatus(Long id) {
        User user = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id));
        user.setActiva(!user.getActiva());
        User saved = accountRepository.save(user);
        return mapToResponse(saved);
    }

    // Validación de negocio
    public void validateAccountCanSend(User user) {
        if (!user.getActiva()) {
            throw new RuntimeException("La cuenta está deshabilitada y no puede enviar dinero. Solo puede recibir.");
        }
    }

    private AccountResponse mapToResponse(User user) {
        AccountResponse response = new AccountResponse();
        response.setId(user.getId());
        response.setUuid(user.getUuid());
        response.setCuentaClave(user.getCuentaClave());
        response.setSaldo(user.getSaldo());
        response.setFechaCreacion(user.getFechaCreacion());
        response.setActiva(user.getActiva());
        return response;
    }
}
