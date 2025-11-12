package com.cuellar.financialstruments.controller;

import com.cuellar.financialstruments.dto.CreateCreditoRequest;
import com.cuellar.financialstruments.dto.CreditoResponse;
import com.cuellar.financialstruments.service.CreditoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/creditos")
public class CreditoController {
    @Autowired
    private CreditoService creditoService;

    @PostMapping
    public ResponseEntity<CreditoResponse> create(@Valid @RequestBody CreateCreditoRequest request) {
        CreditoResponse created = creditoService.createCredito(request);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<CreditoResponse>> getAll() {
        return ResponseEntity.ok(creditoService.getAllCreditos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(creditoService.getById(id));
    }

    @GetMapping("/user/{userUUID}")
    public ResponseEntity<List<CreditoResponse>> getByUserUUID(@PathVariable String userUUID) {
        return ResponseEntity.ok(creditoService.getByUserUUID(userUUID));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        creditoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
