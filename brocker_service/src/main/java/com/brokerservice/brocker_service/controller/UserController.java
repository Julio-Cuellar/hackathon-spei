package com.brokerservice.brocker_service.controller;

import com.brokerservice.brocker_service.dto.CreateUserRequestDTO;
import com.brokerservice.brocker_service.dto.UserResponseDTO;
import com.brokerservice.brocker_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Crear nuevo usuario
     * El UUID DEBE venir en el body del request
     *
     * POST /api/v1/users
     * Body: { "userUUID": "550e8400-e29b-41d4-a716-446655440000", "balance": 50000.00 }
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequestDTO request) {
        try {
            log.info(" Recibiendo solicitud para crear usuario con UUID: {}", request.getUserUUID());

            UserResponseDTO user = userService.createUser(
                    request.getUserUUID(),  // ← UUID recibido del cliente
                    request.getBalance() != null ? request.getBalance() : BigDecimal.valueOf(10000.00)
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (IllegalArgumentException e) {
            log.error("Error al crear usuario: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Obtener todos los usuarios
     * GET /api/v1/users
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Obtener usuario por UUID
     * GET /api/v1/users/550e8400-e29b-41d4-a716-446655440000
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<UserResponseDTO> getUserByUUID(@PathVariable String uuid) {
        try {
            return ResponseEntity.ok(userService.getUserByUUID(uuid));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Actualizar balance de usuario
     * PATCH /api/v1/users/550e8400-e29b-41d4-a716-446655440000/balance?amount=75000.00
     */
    @PatchMapping("/{uuid}/balance")
    public ResponseEntity<?> updateBalance(
            @PathVariable String uuid,
            @RequestParam BigDecimal amount) {
        try {
            userService.updateUserBalance(uuid, amount);
            return ResponseEntity.ok("Balance actualizado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar usuario
     * DELETE /api/v1/users/550e8400-e29b-41d4-a716-446655440000
     */
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable String uuid) {
        try {
            userService.deleteUser(uuid);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
