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

    @GetMapping("/{uuid}")
    public ResponseEntity<SeguroResponse> getByUuid(@PathVariable String uuid) {
        return ResponseEntity.ok(seguroService.getByUuid(uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable String uuid) {
        seguroService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}
