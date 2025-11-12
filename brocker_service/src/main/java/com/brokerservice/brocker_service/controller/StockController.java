package com.brokerservice.brocker_service.controller;

import com.brokerservice.brocker_service.domain.Stock;
import com.brokerservice.brocker_service.dto.StockDTO;
import com.brokerservice.brocker_service.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@Tag(name = "Stocks", description = "API para gestión de acciones disponibles")
public class StockController {

    private final StockService stockService;

    @GetMapping
    @Operation(summary = "Obtener todas las acciones del mercado")
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/portfolio/{userId}")
    @Operation(summary = "Obtener portafolio del usuario")
    public ResponseEntity<List<StockDTO>> getUserPortfolio(@PathVariable long userId) {
        try {
            List<StockDTO> portfolio = stockService.getUserPortfolio(userId);
            return ResponseEntity.ok(portfolio);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{symbol}")
    @Operation(summary = "Obtener acción por símbolo")
    public ResponseEntity<Stock> getStockBySymbol(@PathVariable String symbol) {
        try {
            Stock stock = stockService.getStockBySymbol(symbol);
            return ResponseEntity.ok(stock);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    @Operation(summary = "Crear nueva acción")
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock createdStock = stockService.createStock(stock);
        return ResponseEntity.ok(createdStock);
    }

    @GetMapping("/health")
    @Operation(summary = "Health check")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Stock service is healthy");
    }

}