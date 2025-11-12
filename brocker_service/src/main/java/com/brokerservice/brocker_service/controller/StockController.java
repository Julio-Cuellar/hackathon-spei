package com.brokerservice.brocker_service.controller;

import com.brokerservice.brocker_service.domain.Stock;
import com.brokerservice.brocker_service.dto.StockDTO;
import com.brokerservice.brocker_service.service.PortfolioService;
import com.brokerservice.brocker_service.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@Tag(name = "Stocks", description = "API para gestión de acciones disponibles en el mercado")
public class StockController {

    private final StockService stockService;
    private final PortfolioService portfolioService; //  Nuevo servicio

    @GetMapping
    @Operation(summary = "Obtener todas las acciones del mercado")
    @ApiResponse(responseCode = "200", description = "Lista de acciones")
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        List<StockDTO> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/portfolio/{userId}")
    @Operation(summary = "Obtener portafolio del usuario")
    @ApiResponse(responseCode = "200", description = "Portafolio del usuario")
    public ResponseEntity<List<StockDTO>> getUserPortfolio(@PathVariable Long userId) {
        List<StockDTO> portfolio = portfolioService.getUserPortfolio(userId); //  Usar PortfolioService
        return ResponseEntity.ok(portfolio);
    }

    @PostMapping
    @Operation(summary = "Crear nueva acción")
    @ApiResponse(responseCode = "201", description = "Acción creada exitosamente")
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock createdStock = stockService.createStock(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
    }
}
