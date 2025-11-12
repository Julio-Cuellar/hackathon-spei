package com.cuellar.transactservice.impl;

import com.cuellar.transactservice.domain.User;
import com.cuellar.transactservice.dto.AccountResponse;
import com.cuellar.transactservice.dto.CreateAccountRequest;
import com.cuellar.transactservice.repository.AccountRepository;
import com.cuellar.transactservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AccountRepository accountRepository;

    // Asegura existencia idempotente por userUUID
    @Override
    @Transactional
    public void ensureUser(String userUUID) {
        if (accountRepository.findByUserUUID(userUUID).isPresent()) return;
        User toSave = User.builder().userUUID(userUUID).build();
        try {
            accountRepository.save(toSave);
        } catch (DataIntegrityViolationException ignore) {
            // Otro proceso ya creó el registro
        }
    }

    // Crea si no existe, o devuelve existente
    @Override
    @Transactional
    public AccountResponse createOrGetAccount(CreateAccountRequest req) {
        String userUUID = req.getUserUUID();
        Optional<User> existing = accountRepository.findByUserUUID(userUUID);
        if (existing.isPresent()) {
            return toResponse(existing.get());
        }
        BigDecimal saldoInicial = req.getSaldoInicial() != null ? req.getSaldoInicial() : BigDecimal.ZERO;
        User created = User.builder()
                .userUUID(userUUID)
                .saldo(saldoInicial)
                .build();
        try {
            created = accountRepository.save(created);
        } catch (DataIntegrityViolationException e) {
            created = accountRepository.findByUserUUID(userUUID).orElseThrow();
        }
        return toResponse(created);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountResponse> getAccountByUserUUID(String userUUID) {
        return accountRepository.findByUserUUID(userUUID).map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUserUUID(String userUUID) {
        return accountRepository.findByUserUUID(userUUID);
    }

    @Override
    @Transactional
    public Optional<AccountResponse> toggleAccountStatusByUserUUID(String userUUID) {
        return accountRepository.findByUserUUID(userUUID).map(u -> {
            u.setActiva(!Boolean.TRUE.equals(u.getActiva()));
            return toResponse(u);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByCuentaClave(String cuentaClave) {
        return accountRepository.findByCuentaClave(cuentaClave);
    }

    // Reglas de negocio para transacciones

    @Override
    public void validateAccountCanSend(User account) {
        if (account.getActiva() == null || !account.getActiva()) {
            throw new IllegalStateException("La cuenta origen está inactiva");
        }
    }

    @Override
    public void validateAccountCanReceive(User account) {
        if (account.getActiva() == null || !account.getActiva()) {
            throw new IllegalStateException("La cuenta destino está inactiva");
        }
    }

    @Override
    public void validateSufficientFunds(User account, BigDecimal monto) {
        if (account.getSaldo() == null || account.getSaldo().compareTo(monto) < 0) {
            throw new IllegalStateException("Saldo insuficiente");
        }
    }

    @Override
    public void debit(User account, BigDecimal monto) {
        account.setSaldo(account.getSaldo().subtract(monto));
    }

    @Override
    public void credit(User account, BigDecimal monto) {
        account.setSaldo(account.getSaldo().add(monto));
    }

    private AccountResponse toResponse(User u) {
        return AccountResponse.builder()
                .id(u.getId())
                .userUUID(u.getUserUUID())
                .cuentaClave(u.getCuentaClave())
                .saldo(u.getSaldo())
                .activa(u.getActiva())
                .fechaCreacion(u.getFechaCreacion())
                .build();
    }
}
