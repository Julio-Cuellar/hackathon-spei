package com.cuellar.transactionservice.controller;

import com.cuellar.transactionservice.dto.AccountResponse;
import com.cuellar.transactionservice.dto.CreateAccountRequest;
import com.cuellar.transactionservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    private boolean invalidUser(String userUUID) {
        return userUUID == null || userUUID.isBlank();
    }

    @PostMapping
    public ResponseEntity<?> createAccount(
            @RequestHeader(value = "X-User-UUID", required = false) String userUUID,
            @Valid @RequestBody CreateAccountRequest request) {

        if (invalidUser(userUUID)) {
            log.warn("Missing or blank X-User-UUID in createAccount");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing X-User-UUID header");
        }
        // Si tu diseño requiere 1 sola cuenta por usuario, aquí podrías validar existencia previa.
        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Obtiene la cuenta del usuario autenticado (por UUID del token)
    @GetMapping("/me")
    public ResponseEntity<?> getMyAccount(
            @RequestHeader(value = "X-User-UUID", required = false) String userUUID) {

        if (invalidUser(userUUID)) {
            log.warn("Missing or blank X-User-UUID in getMyAccount");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing X-User-UUID header");
        }
        AccountResponse account = accountService.getAccountByUuid(userUUID);
        return ResponseEntity.ok(account);
    }

    // Si quieres permitir togglear estado solo del propio usuario:
    @PatchMapping("/me/toggle-status")
    public ResponseEntity<?> toggleMyAccountStatus(
            @RequestHeader(value = "X-User-UUID", required = false) String userUUID) {

        if (invalidUser(userUUID)) {
            log.warn("Missing or blank X-User-UUID in toggleMyAccountStatus");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing X-User-UUID header");
        }
        // Busca por UUID y usa su id internamente si tu servicio alterna por id
        AccountResponse current = accountService.getAccountByUuid(userUUID);
        AccountResponse updated = accountService.toggleAccountStatus(current.getId());
        return ResponseEntity.ok(updated);
    }
}
