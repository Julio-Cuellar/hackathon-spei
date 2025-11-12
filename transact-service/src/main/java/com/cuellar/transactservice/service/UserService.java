package com.cuellar.transactservice.service;

import com.cuellar.transactservice.domain.User;
import com.cuellar.transactservice.dto.AccountResponse;
import com.cuellar.transactservice.dto.CreateAccountRequest;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserService {

    // CRUD/Idempotencia de usuario-cuenta
    void ensureUser(String userUUID);
    AccountResponse createOrGetAccount(CreateAccountRequest req);
    Optional<AccountResponse> getAccountByUserUUID(String userUUID);
    Optional<User> findByUserUUID(String userUUID);
    Optional<AccountResponse> toggleAccountStatusByUserUUID(String userUUID);

    // Búsquedas por CLABE
    Optional<User> findByCuentaClave(String cuentaClave);

    // Reglas de negocio para transacciones
    void validateAccountCanSend(User account);
    void validateAccountCanReceive(User account);
    void validateSufficientFunds(User account, BigDecimal monto);
    void debit(User account, BigDecimal monto);
    void credit(User account, BigDecimal monto);
}
