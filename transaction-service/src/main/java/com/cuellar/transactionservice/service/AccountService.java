package com.cuellar.transactionservice.service;

import com.cuellar.transactionservice.domain.Account;
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

    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        Account account = new Account();
        account.setSaldo(request.getSaldoInicial() != null ? request.getSaldoInicial() : BigDecimal.ZERO);

        Account saved = accountRepository.save(account);
        return mapToResponse(saved);
    }

    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AccountResponse getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id));
        return mapToResponse(account);
    }

    public AccountResponse getAccountByUuid(String uuid) {
        Account account = accountRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con UUID: " + uuid));
        return mapToResponse(account);
    }

    public AccountResponse getAccountByCuentaClave(String cuentaClave) {
        Account account = accountRepository.findByCuentaClave(cuentaClave)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con CLABE: " + cuentaClave));
        return mapToResponse(account);
    }

    @Transactional
    public AccountResponse toggleAccountStatus(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id));
        account.setActiva(!account.getActiva());
        Account saved = accountRepository.save(account);
        return mapToResponse(saved);
    }

    // Método auxiliar para validar si una cuenta puede enviar dinero
    public void validateAccountCanSend(Account account) {
        if (!account.getActiva()) {
            throw new RuntimeException("La cuenta está deshabilitada y no puede enviar dinero. Solo puede recibir.");
        }
    }

    private AccountResponse mapToResponse(Account account) {
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setUuid(account.getUuid());
        response.setCuentaClave(account.getCuentaClave());
        response.setSaldo(account.getSaldo());
        response.setFechaCreacion(account.getFechaCreacion());
        response.setActiva(account.getActiva());
        return response;
    }
}
