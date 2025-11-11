package com.cuellar.transactionservice.controller;

import com.cuellar.transactionservice.dto.AccountResponse;
import com.cuellar.transactionservice.dto.CreateAccountRequest;
import com.cuellar.transactionservice.service.AccountService;
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
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<AccountResponse> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        AccountResponse account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<AccountResponse> getAccountByUuid(@PathVariable String uuid) {
        AccountResponse account = accountService.getAccountByUuid(uuid);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/clabe/{cuentaClave}")
    public ResponseEntity<AccountResponse> getAccountByCuentaClave(@PathVariable String cuentaClave) {
        AccountResponse account = accountService.getAccountByCuentaClave(cuentaClave);
        return ResponseEntity.ok(account);
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<AccountResponse> toggleAccountStatus(@PathVariable Long id) {
        AccountResponse account = accountService.toggleAccountStatus(id);
        return ResponseEntity.ok(account);
    }
}
