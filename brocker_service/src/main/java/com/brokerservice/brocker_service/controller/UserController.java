package com.brokerservice.brocker_service.controller;

import com.brokerservice.brocker_service.domain.User;
import com.brokerservice.brocker_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "API para gestión de usuarios")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Crear nuevo usuario")
    public ResponseEntity<?> createUser() { // ✅ Sin parámetros
        try {
            User user = userService.createUser();
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creando usuario: " + e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Error obteniendo usuarios: " + e.getMessage());
        }
    }

    // ✅ CAMBIADO: UUID por Long
    @GetMapping("/{userId}")
    @Operation(summary = "Obtener usuario por ID")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Usuario no encontrado: " + e.getMessage());
        }
    }

    // ❌ ELIMINAR: Este método ya no sirve sin username
    // @GetMapping("/username/{username}")

    // ✅ CAMBIADO: UUID por Long
    @GetMapping("/{userId}/balance")
    @Operation(summary = "Obtener saldo del usuario")
    public ResponseEntity<?> getUserBalance(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(new BalanceResponse(user.getBalance()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error obteniendo saldo: " + e.getMessage());
        }
    }

    // ✅ CAMBIADO: UUID por Long
    @PutMapping("/{userId}/balance")
    @Operation(summary = "Actualizar saldo del usuario")
    public ResponseEntity<?> updateUserBalance(
            @PathVariable Long userId,
            @RequestBody UpdateBalanceRequest request) {
        try {
            User user = userService.updateUserBalance(userId, request.getNewBalance());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error actualizando saldo: " + e.getMessage());
        }
    }


    // ✅ DTOs actualizados
    @Data
    public static class UpdateBalanceRequest {
        private BigDecimal newBalance;
    }

    @Data
    @RequiredArgsConstructor
    public static class BalanceResponse {
        private final BigDecimal balance;
    }
}