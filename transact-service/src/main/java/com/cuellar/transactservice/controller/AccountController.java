package com.cuellar.transactservice.controller;

import com.cuellar.transactservice.dto.AccountResponse;
import com.cuellar.transactservice.dto.CreateAccountRequest;
import com.cuellar.transactservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final UserService userService;

    private boolean invalidUser(String userUUID) {
        return userUUID == null || userUUID.isBlank();
    }

    @PostMapping
    public ResponseEntity<?> createAccount(
            @RequestHeader(value = "X-User-UUID", required = false) String userUUID,
            @Valid @RequestBody CreateAccountRequest request) {

        if (invalidUser(userUUID)) {
            log.warn("Missing or blank X-User-UUID in createAccount");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Missing X-User-UUID header"));
        }

        // Impone el UUID del header como identidad fuente
        CreateAccountRequest effective = request;
        if (request.getUserUUID() == null || request.getUserUUID().isBlank()) {
            try {
                request.setUserUUID(userUUID);
            } catch (Exception ex) {
                effective = CreateAccountRequest.builder()
                        .saldoInicial(request.getSaldoInicial())
                        .userUUID(userUUID)
                        .build();
            }
        } else if (!userUUID.equals(request.getUserUUID())) {
            try {
                request.setUserUUID(userUUID);
            } catch (Exception ex) {
                effective = CreateAccountRequest.builder()
                        .saldoInicial(request.getSaldoInicial())
                        .userUUID(userUUID)
                        .build();
            }
        }

        // Garantiza existencia y crea si no existe (idempotente)
        userService.ensureUser(userUUID);
        AccountResponse response = userService.createOrGetAccount(effective);
        log.info("Account ensured/created for userUUID={}", userUUID);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyAccount(
            @RequestHeader(value = "X-User-UUID", required = false) String userUUID) {

        if (invalidUser(userUUID)) {
            log.warn("Missing or blank X-User-UUID in getMyAccount");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Missing X-User-UUID header"));
        }

        return userService.getAccountByUserUUID(userUUID)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("User not found for userUUID={}", userUUID);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ErrorResponse("User not found"));
                });
    }

    @PatchMapping("/me/toggle-status")
    public ResponseEntity<?> toggleMyAccountStatus(
            @RequestHeader(value = "X-User-UUID", required = false) String userUUID) {

        if (invalidUser(userUUID)) {
            log.warn("Missing or blank X-User-UUID in toggleMyAccountStatus");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Missing X-User-UUID header"));
        }

        return userService.toggleAccountStatusByUserUUID(userUUID)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("User not found for userUUID={}", userUUID);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ErrorResponse("User not found"));
                });
    }

    @Value
    static class ErrorResponse {
        String message;
    }
}
