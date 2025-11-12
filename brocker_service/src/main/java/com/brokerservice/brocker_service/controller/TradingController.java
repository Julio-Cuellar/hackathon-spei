package com.brokerservice.brocker_service.controller;

import com.brokerservice.brocker_service.dto.TradeHistoryDTO;
import com.brokerservice.brocker_service.dto.TradeRequestDTO;
import com.brokerservice.brocker_service.dto.TradeResponseDTO;
import com.brokerservice.brocker_service.service.TradingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @PostMapping("/execute")
    @Operation(summary = "Ejecutar operación de trading",
            description = "Ejecuta una compra o venta de acciones")
    @ApiResponse(responseCode = "200", description = "Operación ejecutada exitosamente")
    @ApiResponse(responseCode = "400", description = "Validación fallida o fondos insuficientes")
    public ResponseEntity<TradeResponseDTO> executeTrade(
            @Valid @RequestBody TradeRequestDTO request) {
        TradeResponseDTO response = tradingService.executeTrade(request);
        return ResponseEntity.ok(response);
    }

    //  CORREGIDO: Retorna TradeHistoryDTO
    @GetMapping("/history/{userId}")
    @Operation(summary = "Historial de operaciones del usuario",
            description = "Retorna todas las operaciones realizadas por un usuario ordenadas por fecha")
    @ApiResponse(responseCode = "200", description = "Historial de operaciones")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<List<TradeHistoryDTO>> getUserTradeHistory(
            @PathVariable Long userId) {
        List<TradeHistoryDTO> history = tradingService.getUserTradeHistory(String.valueOf(userId));
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{userId}/stocks/{symbol}/quantity")
    @Operation(summary = "Obtener cantidad disponible de una acción",
            description = "Retorna la cantidad neta de acciones que posee el usuario")
    @ApiResponse(responseCode = "200", description = "Cantidad disponible")
    public ResponseEntity<Map<String, Object>> getAvailableStockQuantity(
            @PathVariable Long userId,
            @PathVariable String symbol) {
        int available = tradingService.getAvailableStockQuantity(String.valueOf(userId), symbol);
        return ResponseEntity.ok(Map.of(
                "userId", userId,
                "symbol", symbol,
                "availableQuantity", available
        ));
    }

    @GetMapping("/health")
    @Operation(summary = "Health check",
            description = "Verifica que el servicio de trading esté operativo")
    @ApiResponse(responseCode = "200", description = "Servicio operativo")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Trading service is healthy ");
    }
}
