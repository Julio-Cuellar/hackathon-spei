package com.cuellar.transactservice.controller;

import com.cuellar.transactservice.domain.TransactionType;
import com.cuellar.transactservice.domain.User;
import com.cuellar.transactservice.dto.CreateUserRequest;
import com.cuellar.transactservice.dto.PagoServicioRequest;
import com.cuellar.transactservice.dto.TransactionResponse;
import com.cuellar.transactservice.dto.TransferenciaRequest;
import com.cuellar.transactservice.dto.UserResponse;
import com.cuellar.transactservice.service.TransactionService;
import com.cuellar.transactservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/transact")
@RequiredArgsConstructor
@Slf4j
public class TransactController {

    private final UserService userService;
    private final TransactionService transactionService;

    // Utilidad para resolver el UUID del usuario desde el Gateway
    private String resolveUserUUID(String authHeader, String userHeader) {
        if (StringUtils.hasText(userHeader)) {
            return userHeader.trim();
        }
        if (!StringUtils.hasText(authHeader)) {
            throw new IllegalArgumentException("Falta credencial de usuario (Authorization o X-User-UUID)");
        }
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7).trim() : authHeader.trim();
        // Asumiendo que el Gateway ya validó y podría inyectar el UUID como cabecera.
        // Si necesitas decodificar el JWT aquí, inyecta tu JwtService y extrae el claim de UUID.
        // Para mantenerlo simple, exigimos X-User-UUID o que este servicio tenga JwtService.
        throw new IllegalArgumentException("Provee X-User-UUID o integra JwtService para extraer el UUID del token");
    }

    // Provisión/lectura de cuenta por UUID (idempotente)
    @PostMapping("/account/ensure")
    public ResponseEntity<UserResponse> ensureAccount(
            @RequestHeader(value = "X-User-UUID", required = false) String userUUID,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        String resolved = resolveUserUUID(authHeader, userUUID);
        User ensured = userService.ensureUser(resolved);
        UserResponse dto = userService.getAccountByUuid(ensured.getUserUUID());
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    // Crear cuenta manualmente con saldo inicial (no usa UUID del token, es administrativa)
    @PostMapping("/account")
    public ResponseEntity<UserResponse> createAccount(@Valid @RequestBody CreateUserRequest request) {
        UserResponse created = userService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Obtener mi cuenta por UUID del Gateway
    @GetMapping("/account/me")
    public ResponseEntity<UserResponse> getMyAccount(
            @RequestHeader(value = "X-User-UUID", required = false) String userUUID,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        String resolved = resolveUserUUID(authHeader, userUUID);
        return ResponseEntity.ok(userService.getAccountByUuid(resolved));
    }

    // Transferencia entre cuentas (usa CLABEs en el body)
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(
            @Valid @RequestBody TransferenciaRequest request,
            @RequestHeader(value = "X-User-UUID", required = false) String userUUID,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        // Si necesitas validar que la cuenta origen pertenece al usuario autenticado, aquí puedes cruzar userUUID.
        TransactionResponse tx = transactionService.createTransferencia(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tx);
    }

    // Pago de servicio (cargo a la cuenta del usuario autenticado si tu dominio lo exige)
    @PostMapping("/service-payment")
    public ResponseEntity<TransactionResponse> payService(
            @Valid @RequestBody PagoServicioRequest request,
            @RequestHeader(value = "X-User-UUID", required = false) String userUUID,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        TransactionResponse tx = transactionService.createPagoServicio(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tx);
    }

    // Listar todas las transacciones (podrías filtrar por UUID si agregas consultas por usuario)
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponse>> listAll() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    // Listar transacciones por tipo
    @GetMapping("/transactions/type/{type}")
    public ResponseEntity<List<TransactionResponse>> listByType(@PathVariable TransactionType type) {
        return ResponseEntity.ok(transactionService.getTransactionsByType(type));
    }

    // Obtener detalle de transacción por id interno
    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }
}
