package com.cuellar.financialstruments.controller;

import com.cuellar.financialstruments.dto.CreateHipotecaRequest;
import com.cuellar.financialstruments.dto.HipotecaResponse;
import com.cuellar.financialstruments.service.HipotecaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hipotecas")
public class HipotecaController {
    @Autowired
    private HipotecaService hipotecaService;

    @PostMapping
    public ResponseEntity<HipotecaResponse> create(@Valid @RequestBody CreateHipotecaRequest request) {
        HipotecaResponse created = hipotecaService.createHipoteca(request);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<HipotecaResponse>> getAll() {
        return ResponseEntity.ok(hipotecaService.getAllHipotecas());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<HipotecaResponse> getByUuid(@PathVariable String uuid) {
        return ResponseEntity.ok(hipotecaService.getByUuid(uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable String uuid) {
        hipotecaService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}
