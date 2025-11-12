package com.brokerservice.brocker_service.controller;

import com.brokerservice.brocker_service.domain.TradeOperation;
import com.brokerservice.brocker_service.dto.TradeRequestDTO;
import com.brokerservice.brocker_service.dto.TradeResponseDTO;
import com.brokerservice.brocker_service.service.TradingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trading")
@RequiredArgsConstructor
@Tag(name = "Trading", description = "API para operaciones de compra y venta de acciones")
public class TradingController {

    private final TradingService tradingService;

    // ✅ CORREGIDO: Usar Long en lugar de UUID
    @PostMapping("/execute")
    @Operation(summary = "Ejecutar operación de trading")
    public ResponseEntity<TradeResponseDTO> executeTrade(@RequestBody TradeRequestDTO request) {
        // Validar que el request tenga userId
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("userId es requerido");
        }

        // ✅ CAMBIO: No necesitas convertir, usa Long directamente
        TradeResponseDTO response = tradingService.executeTrade(request, Long.parseLong(request.getUserId()));
        return ResponseEntity.ok(response);
    }

        // ✅ ENDPOINT PARA OBTENER CANTIDAD DISPONIBLE
        @GetMapping("/{userId}/stocks/{symbol}/quantity")
        @Operation(summary = "Obtener cantidad disponible de una acción en el portafolio")
        public ResponseEntity<?> getAvailableStockQuantity(
                @PathVariable Long userId,
                @PathVariable String symbol) {
            try {
                int available = tradingService.getAvailableStockQuantity(userId, symbol);
                return ResponseEntity.ok(Map.of(
                        "userId", userId,
                        "symbol", symbol,
                        "availableQuantity", available
                ));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error: " + e.getMessage());
            }
        }

    // ✅ CORREGIDO: Usar Long en lugar de UUID
    @GetMapping("/operations")
    @Operation(summary = "Obtener historial de operaciones")
    public ResponseEntity<List<TradeOperation>> getUserOperations(@RequestParam Long userId) {
        // ✅ CAMBIO: Recibir Long directamente
        List<TradeOperation> operations = tradingService.getUserOperations(userId);
        return ResponseEntity.ok(operations);
    }

    @GetMapping("/health")
    @Operation(summary = "Health check")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Trading service is healthy");
    }
}