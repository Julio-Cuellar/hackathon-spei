package com.brokerservice.brocker_service.service;

import com.brokerservice.brocker_service.domain.Stock;
import com.brokerservice.brocker_service.domain.TradeOperation;
import com.brokerservice.brocker_service.dto.StockDTO;
import com.brokerservice.brocker_service.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    @Lazy
    private final TradingService tradingService;

    // ✅ MÉTODO QUE FALTA - getUserPortfolio
    public List<StockDTO> getUserPortfolio(long userId) {
        log.debug("📊 Obteniendo portafolio para usuario: {}", userId);

        List<Stock> allStocks = stockRepository.findAll();
        List<TradeOperation> userOperations = tradingService.getUserOperations(userId);

        Map<String, Integer> userQuantities = calculateUserQuantities(userOperations);

        List<StockDTO> portfolio = allStocks.stream()
                .filter(stock -> userQuantities.getOrDefault(stock.getSymbol(), 0) > 0)
                .map(stock -> {
                    int quantity = userQuantities.get(stock.getSymbol());
                    log.debug("Acción en portafolio: {} - {} acciones", stock.getSymbol(), quantity);
                    return StockDTO.builder()
                            .symbol(stock.getSymbol())
                            .name(stock.getName())
                            .quantity(quantity)
                            .currentPrice(stock.getCurrentPrice())
                            .currency("USD")
                            .build();
                })
                .collect(Collectors.toList());

        log.info("✅ Portafolio obtenido: usuario={}, acciones={}", userId, portfolio.size());
        return portfolio;
    }

    // ✅ MÉTODO ADICIONAL - Para obtener una acción específica del portfolio
    public StockDTO getStockWithUserQuantity(String symbol, long userId) {
        Stock stock = getStockBySymbol(symbol);
        List<TradeOperation> userOperations = tradingService.getUserOperations(userId);
        int userQuantity = calculateUserQuantityForSymbol(userOperations, symbol);

        if (userQuantity <= 0) {
            throw new RuntimeException("No tienes acciones de " + symbol);
        }

        return StockDTO.builder()
                .symbol(stock.getSymbol())
                .name(stock.getName())
                .quantity(userQuantity)
                .currentPrice(stock.getCurrentPrice())
                .currency("USD")
                .build();
    }

    // ✅ MÉTODOS EXISTENTES
    public List<Stock> getAllStocks() {
        log.debug("Obteniendo todas las acciones del mercado");
        List<Stock> stocks = stockRepository.findAll();
        log.info("Acciones obtenidas: cantidad={}", stocks.size());
        return stocks;
    }

    public Stock getStockBySymbol(String symbol) {
        log.debug("🔍 Buscando stock por símbolo: {}", symbol);

        // ✅ CORREGIDO: Usar findBySymbol, no findById
        Stock stock = stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> {
                    log.warn("❌ Stock no encontrado: {}", symbol);
                    return new RuntimeException("Stock no encontrado: " + symbol);
                });

        log.debug("✅ Stock encontrado: {} - {} (${})", symbol, stock.getName(), stock.getCurrentPrice());
        return stock;
    }

    public Stock createStock(Stock stock) {
        log.info("➕ Creando nueva acción: {}", stock.getSymbol());
        Stock savedStock = stockRepository.save(stock);
        log.info("✅ Acción creada: {} - {}", savedStock.getSymbol(), savedStock.getName());
        return savedStock;
    }

    public List<Stock> searchStocks(String query) {
        log.debug("Buscando acciones con query: {}", query);
        List<Stock> stocks = stockRepository.findByNameContainingIgnoreCaseOrSymbolContainingIgnoreCase(query, query);
        log.info("Búsqueda completada: query={}, resultados={}", query, stocks.size());
        return stocks;
    }

    // ✅ MÉTODOS PRIVADOS DE APOYO
    private Map<String, Integer> calculateUserQuantities(List<TradeOperation> operations) {
        Map<String, Integer> quantities = new HashMap<>();
        for (TradeOperation op : operations) {
            String symbol = op.getSymbol();
            int quantityChange = "BUY".equals(op.getOperationType()) ?
                    op.getContractQuantity() : -op.getContractQuantity();
            quantities.merge(symbol, quantityChange, Integer::sum);
        }
        quantities.entrySet().removeIf(entry -> entry.getValue() <= 0);
        return quantities;
    }

    private int calculateUserQuantityForSymbol(List<TradeOperation> operations, String symbol) {
        return operations.stream()
                .filter(op -> symbol.equals(op.getSymbol()))
                .mapToInt(op -> "BUY".equals(op.getOperationType()) ?
                        op.getContractQuantity() : -op.getContractQuantity())
                .sum();
    }

}