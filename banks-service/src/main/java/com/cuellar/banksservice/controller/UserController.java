package com.cuellar.banksservice.controller;

import com.cuellar.banksservice.dto.CreateUserResponse;
import com.cuellar.banksservice.dto.UserDTO;
import com.cuellar.banksservice.dto.UpdateCuentaClabeRequest;
import com.cuellar.banksservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{UUID}")
    public ResponseEntity<UserDTO> getUserByUUID(@PathVariable String UUID) {
        UserDTO user = userService.getUserByUUID(UUID);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{UUID}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String UUID, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(UUID, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    // NUEVO ENDPOINT: Agregar/actualizar cuenta CLABE
    @PostMapping("/{UUID}/cuenta-clabe")
    public ResponseEntity<UserDTO> addCuentaClabe(
            @PathVariable String UUID,
            @Valid @RequestBody UpdateCuentaClabeRequest request) {
        UserDTO updatedUser = userService.addCuentaClabe(UUID, request);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{UUID}")
    public ResponseEntity<Void> deleteUser(@PathVariable String UUID) {
        userService.deleteUser(UUID);
        return ResponseEntity.noContent().build();
    }
}
