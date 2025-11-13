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
    public ResponseEntity<CreditoResponse> create(@RequestBody CreateCreditoRequest request) {
        CreditoResponse response = creditoService.createCredito(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CreditoResponse>> getAll() {
        return ResponseEntity.ok(creditoService.getAllCreditos());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CreditoResponse> getByUuid(@PathVariable String uuid) {
        return ResponseEntity.ok(creditoService.getByUuid(uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable String uuid) {
        creditoService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}
