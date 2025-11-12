package com.cuellar.financialstruments.controller;

import com.cuellar.financialstruments.service.BuroCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buro-credito")
public class BuroCreditoController {
    @Autowired
    private BuroCreditoService buroCreditoService;

    @GetMapping("/check/{cuentaUuid}")
    public ResponseEntity<Boolean> check(@PathVariable String cuentaUuid) {
        boolean enBuro = buroCreditoService.isEnBuro(cuentaUuid);
        return ResponseEntity.ok(enBuro);
    }

    @PostMapping
    public ResponseEntity<Void> marcarEnBuro(@RequestBody String cuentaUuid) {
        buroCreditoService.marcarEnBuro(cuentaUuid);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{cuentaUuid}")
    public ResponseEntity<Void> borrarDeBuro(@PathVariable String cuentaUuid) {
        buroCreditoService.borrarDeBuro(cuentaUuid);
        return ResponseEntity.noContent().build();
    }
}
