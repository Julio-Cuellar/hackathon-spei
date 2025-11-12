package com.cuellar.transactionservice.controller;

import com.cuellar.transactionservice.dto.UserResponse;
import com.cuellar.transactionservice.dto.CreateUserRequest;
import com.cuellar.transactionservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createAccount(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = userService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllAccounts() {
        List<UserResponse> accounts = userService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getAccountById(@PathVariable Long id) {
        UserResponse account = userService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<UserResponse> getAccountByUuid(@PathVariable String uuid) {
        UserResponse account = userService.getAccountByUuid(uuid);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/clabe/{cuentaClave}")
    public ResponseEntity<UserResponse> getAccountByCuentaClave(@PathVariable String cuentaClave) {
        UserResponse account = userService.getAccountByCuentaClave(cuentaClave);
        return ResponseEntity.ok(account);
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<UserResponse> toggleAccountStatus(@PathVariable Long id) {
        UserResponse account = userService.toggleAccountStatus(id);
        return ResponseEntity.ok(account);
    }
}
