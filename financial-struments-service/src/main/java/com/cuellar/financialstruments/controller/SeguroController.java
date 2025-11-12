package com.cuellar.financialstruments.controller;

import com.cuellar.financialstruments.dto.CreateSeguroRequest;
import com.cuellar.financialstruments.dto.SeguroResponse;
import com.cuellar.financialstruments.service.SeguroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/seguros")
public class SeguroController {
    @Autowired
    private SeguroService seguroService;

    @PostMapping
    public ResponseEntity<SeguroResponse> create(@Valid @RequestBody CreateSeguroRequest request) {
        SeguroResponse created = seguroService.createSeguro(request);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<SeguroResponse>> getAll() {
        return ResponseEntity.ok(seguroService.getAllSeguros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeguroResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(seguroService.getById(id));
    }

    @GetMapping("/user/{userUUID}")
    public ResponseEntity<List<SeguroResponse>> getByUserUUID(@PathVariable String userUUID) {
        return ResponseEntity.ok(seguroService.getByUserUUID(userUUID));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seguroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
