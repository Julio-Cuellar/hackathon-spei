package com.cuellar.financialstruments.controller;

import com.cuellar.financialstruments.service.BuroCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/buro-credito")
public class BuroCreditoController {
    @Autowired
    private BuroCreditoService buroCreditoService;

    @GetMapping("/check/{userUUID}")
    public ResponseEntity<Boolean> check(@PathVariable String userUUID) {
        boolean enBuro = buroCreditoService.isEnBuro(userUUID);
        return ResponseEntity.ok(enBuro);
    }

    @PostMapping
    public ResponseEntity<Void> marcarEnBuro(@RequestBody Map<String, String> request) {
        String userUUID = request.get("userUUID");
        String rfc = request.get("rfc");
        buroCreditoService.marcarEnBuro(userUUID, rfc);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{userUUID}")
    public ResponseEntity<Void> borrarDeBuro(@PathVariable String userUUID) {
        buroCreditoService.borrarDeBuro(userUUID);
        return ResponseEntity.noContent().build();
    }
}
