package com.brokerservice.brocker_service.service;

import com.brokerservice.brocker_service.domain.TradeOperation;
import com.brokerservice.brocker_service.domain.User;
import com.brokerservice.brocker_service.dto.TradeRequestDTO;
import com.brokerservice.brocker_service.dto.TradeResponseDTO;
import com.brokerservice.brocker_service.repository.TradeOperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradingService {

    private final TradeOperationRepository tradeOperationRepository;
    private final UserService userService;

    // 📋 LÍMITES CONFIGURABLES
    private static final int MAX_QUANTITY_PER_TRADE = 10_000;
    private static final BigDecimal MIN_TRADE_AMOUNT = new BigDecimal("10.00");
    private static final BigDecimal MIN_PRICE_PER_STOCK = new BigDecimal("0.01");
    private static final BigDecimal MAX_PRICE_PER_STOCK = new BigDecimal("100000.00");

    @Transactional
    public TradeResponseDTO executeTrade(TradeRequestDTO request, long userId) {
        User user = userService.getUserById(userId);

        // ✅ 1. VALIDAR CANTIDAD (entera y positiva)
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser un número positivo");
        }

        // ✅ 2. VALIDAR CANTIDAD MÁXIMA
        if (request.getQuantity() > MAX_QUANTITY_PER_TRADE) {
            throw new IllegalArgumentException(
                    String.format("No puedes operar más de %,d acciones por transacción",
                            MAX_QUANTITY_PER_TRADE)
            );
        }

        // ✅ 3. VALIDAR PRECIO DENTRO DE RANGO RAZONABLE
        if (request.getPrice() == null ||
                request.getPrice().compareTo(MIN_PRICE_PER_STOCK) < 0 ||
                request.getPrice().compareTo(MAX_PRICE_PER_STOCK) > 0) {
            throw new IllegalArgumentException(
                    String.format("El precio debe estar entre $%.2f y $%,.2f por acción",
                            MIN_PRICE_PER_STOCK, MAX_PRICE_PER_STOCK)
            );
        }

        // ✅ 4. CALCULAR MONTO TOTAL
        BigDecimal totalAmount = request.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        // ✅ 5. VALIDAR MONTO MÍNIMO
        if (totalAmount.compareTo(MIN_TRADE_AMOUNT) < 0) {
            throw new IllegalArgumentException(
                    String.format("El monto mínimo de operación es $%.2f USD. " +
                                    "Monto actual: $%.2f. Intenta comprar más acciones o con precio mayor.",
                            MIN_TRADE_AMOUNT, totalAmount)
            );
        }

        // ✅ 6. VALIDAR VENTA - Verificar que tiene suficientes acciones
        if ("SELL".equals(request.getOperationType())) {
            int availableQuantity = getAvailableStockQuantity(userId, request.getSymbol());
            if (availableQuantity < request.getQuantity()) {
                throw new IllegalArgumentException(
                        String.format("No tienes suficientes acciones para vender. " +
                                        "Disponibles: %,d, Intentas vender: %,d",
                                availableQuantity, request.getQuantity())
                );
            }
        }

        // ✅ 7. VALIDAR SALDO PARA COMPRA
        if ("BUY".equals(request.getOperationType()) && !user.hasSufficientBalance(totalAmount)) {
            throw new IllegalArgumentException(
                    String.format("Saldo insuficiente. Necesitas: $%.2f, Disponible: $%.2f",
                            totalAmount, user.getBalance())
            );
        }

        // ✅ 8. CREAR OPERACIÓN
        TradeOperation operation = TradeOperation.builder()
                .user(user)
                .operationType(request.getOperationType())
                .symbol(request.getSymbol())
                .operationName(generateOperationName(request))
                .contractQuantity(request.getQuantity())
                .stockPrice(request.getPrice())
                .totalAmount(totalAmount)
                .status("EXECUTED")
                .operationTime(LocalDateTime.now())
                .build();

        // ✅ 9. ACTUALIZAR SALDO DEL USUARIO
        if ("BUY".equals(request.getOperationType())) {
            user.deductBalance(totalAmount);
        } else {
            user.addBalance(totalAmount);
        }

        TradeOperation savedOperation = tradeOperationRepository.save(operation);

        return TradeResponseDTO.builder()
                .operationId(savedOperation.getId())
                .operationType(savedOperation.getOperationType())
                .symbol(savedOperation.getSymbol())
                .quantity(savedOperation.getContractQuantity())
                .price(savedOperation.getStockPrice())
                .totalAmount(savedOperation.getTotalAmount())
                .status(savedOperation.getStatus())
                .message("Operación ejecutada exitosamente")
                .build();
    }

    /**
     * ✅ OBTENER CANTIDAD DISPONIBLE DE ACCIONES
     */
    public int getAvailableStockQuantity(Long userId, String symbol) {
        List<TradeOperation> userOperations = getUserOperations(userId);

        int netQuantity = userOperations.stream()
                .filter(op -> symbol.equals(op.getSymbol()))
                .mapToInt(op -> "BUY".equals(op.getOperationType()) ?
                        op.getContractQuantity() : -op.getContractQuantity())
                .sum();

        return Math.max(netQuantity, 0);
    }

    public List<TradeOperation> getUserOperations(Long userId) {
        return tradeOperationRepository.findByUser_Id(userId);
    }

    private String generateOperationName(TradeRequestDTO request) {
        return String.format("%s %,d acciones de %s",
                request.getOperationType(),
                request.getQuantity(),
                request.getSymbol());
    }

    // 📊 MÉTODOS PARA CONSULTAR LÍMITES (útil para el frontend)
    public int getMaxQuantityPerTrade() {
        return MAX_QUANTITY_PER_TRADE;
    }

    public BigDecimal getMinTradeAmount() {
        return MIN_TRADE_AMOUNT;
    }

    public BigDecimal getMinPricePerStock() {
        return MIN_PRICE_PER_STOCK;
    }

    public BigDecimal getMaxPricePerStock() {
        return MAX_PRICE_PER_STOCK;
    }
}