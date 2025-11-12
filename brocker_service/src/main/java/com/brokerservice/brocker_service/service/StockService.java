package com.brokerservice.brocker_service.service;

import com.brokerservice.brocker_service.domain.Stock;
import com.brokerservice.brocker_service.domain.TradeOperation;
import com.brokerservice.brocker_service.dto.StockDTO;
import com.brokerservice.brocker_service.dto.TradeHistoryDTO;
import com.brokerservice.brocker_service.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StockService {

    private final StockRepository stockRepository;
    private final TradingService tradingService;

    // Constructor con @Lazy para evitar dependencia circular
    public StockService(StockRepository stockRepository,
                        @Lazy TradingService tradingService) {
        this.stockRepository = stockRepository;
        this.tradingService = tradingService;
    }

    @Transactional(readOnly = true)
    public List<StockDTO> getAllStocks() {
        log.debug("Obteniendo todas las acciones del mercado");
        return stockRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StockDTO> getUserPortfolio(Long userId) {
        log.debug("Obteniendo portafolio para usuario: {}", userId);

        List<Stock> allStocks = stockRepository.findAll();
        List<TradeHistoryDTO> userOperations = tradingService.getUserTradeHistory(userId);

        Map<String, List<TradeHistoryDTO>> operationsBySymbol = userOperations.stream()
                .collect(Collectors.groupingBy(TradeHistoryDTO::getSymbol));

        List<StockDTO> portfolio = new ArrayList<>();

        for (Stock stock : allStocks) {
            List<TradeHistoryDTO> stockOperations = operationsBySymbol.get(stock.getSymbol());

            if (stockOperations == null || stockOperations.isEmpty()) {
                continue;
            }

            int totalQuantity = 0;
            BigDecimal totalInvested = BigDecimal.ZERO;

            for (TradeHistoryDTO op : stockOperations) {
                if ("BUY".equals(op.getOperationType())) {
                    totalQuantity += op.getContractQuantity();
                    totalInvested = totalInvested.add(op.getTotalAmount());
                } else if ("SELL".equals(op.getOperationType())) {
                    totalQuantity -= op.getContractQuantity();
                    totalInvested = totalInvested.subtract(op.getTotalAmount());
                }
            }

            if (totalQuantity > 0) {
                BigDecimal currentValue = stock.getCurrentPrice()
                        .multiply(BigDecimal.valueOf(totalQuantity));
                BigDecimal profitLoss = currentValue.subtract(totalInvested);
                BigDecimal profitLossPercentage = totalInvested.compareTo(BigDecimal.ZERO) > 0
                        ? profitLoss.divide(totalInvested, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
                        : BigDecimal.ZERO;

                StockDTO dto = mapToDTO(stock);
                dto.setQuantityOwned(totalQuantity);
                dto.setTotalInvestment(totalInvested);
                dto.setCurrentValue(currentValue);
                dto.setProfitLoss(profitLoss);
                dto.setProfitLossPercentage(profitLossPercentage);

                portfolio.add(dto);
            }
        }

        log.info("Portafolio calculado: {} acciones diferentes", portfolio.size());
        return portfolio;
    }

    @Transactional
    public Stock createStock(Stock stock) {
        log.info(" Creando nueva acción: {}", stock.getSymbol());

        if (stockRepository.existsBySymbol(stock.getSymbol())) {
            log.error("Ya existe una acción con el símbolo: {}", stock.getSymbol());
            throw new IllegalArgumentException("Ya existe una acción con el símbolo: " + stock.getSymbol());
        }

        Stock savedStock = stockRepository.save(stock);
        log.info("Acción creada exitosamente: {}", savedStock.getSymbol());
        return savedStock;
    }

    @Transactional(readOnly = true)
    public Stock findStockBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> {
                    log.error("Acción no encontrada con símbolo: {}", symbol);
                    return new IllegalArgumentException("Acción no encontrada: " + symbol);
                });
    }

    private StockDTO mapToDTO(Stock stock) {
        return StockDTO.builder()
                .id(stock.getId())
                .symbol(stock.getSymbol())
                .name(stock.getName())
                .currentPrice(stock.getCurrentPrice())
                .currency(stock.getCurrency())
                .isActive(stock.getIsActive())
                .build();
    }
}
