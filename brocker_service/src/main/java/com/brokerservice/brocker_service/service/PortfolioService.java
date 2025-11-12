package com.brokerservice.brocker_service.service;

import com.brokerservice.brocker_service.domain.Stock;
import com.brokerservice.brocker_service.domain.TradeOperation;
import com.brokerservice.brocker_service.dto.StockDTO;
import com.brokerservice.brocker_service.repository.StockRepository;
import com.brokerservice.brocker_service.repository.TradeOperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final StockRepository stockRepository;
    private final TradeOperationRepository tradeOperationRepository;

    @Transactional(readOnly = true)
    public List<StockDTO> getUserPortfolio(Long userId) {
        log.debug(" Calculando portafolio para usuario: {}", userId);

        List<Stock> allStocks = stockRepository.findAll();
        List<TradeOperation> userOperations = tradeOperationRepository
                .findByUserIdOrderByOperationTimeDesc(userId);

        Map<String, List<TradeOperation>> operationsBySymbol = userOperations.stream()
                .collect(Collectors.groupingBy(TradeOperation::getSymbol));

        List<StockDTO> portfolio = new ArrayList<>();

        for (Stock stock : allStocks) {
            List<TradeOperation> stockOperations = operationsBySymbol.get(stock.getSymbol());

            if (stockOperations == null || stockOperations.isEmpty()) {
                continue;
            }

            int totalQuantity = 0;
            BigDecimal totalInvested = BigDecimal.ZERO;

            for (TradeOperation op : stockOperations) {
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

                StockDTO dto = StockDTO.builder()
                        .id(stock.getId())
                        .symbol(stock.getSymbol())
                        .name(stock.getName())
                        .currentPrice(stock.getCurrentPrice())
                        .currency(stock.getCurrency())
                        .isActive(stock.getIsActive())
                        .quantityOwned(totalQuantity)
                        .totalInvestment(totalInvested)
                        .currentValue(currentValue)
                        .profitLoss(profitLoss)
                        .profitLossPercentage(profitLossPercentage)
                        .build();

                portfolio.add(dto);
            }
        }

        log.info(" Portafolio calculado: {} acciones diferentes", portfolio.size());
        return portfolio;
    }
}
