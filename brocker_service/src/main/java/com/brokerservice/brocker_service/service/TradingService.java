package com.brokerservice.brocker_service.service;

import com.brokerservice.brocker_service.domain.Stock;
import com.brokerservice.brocker_service.domain.TradeOperation;
import com.brokerservice.brocker_service.domain.User;
import com.brokerservice.brocker_service.dto.TradeHistoryDTO;
import com.brokerservice.brocker_service.dto.TradeRequestDTO;
import com.brokerservice.brocker_service.dto.TradeResponseDTO;
import com.brokerservice.brocker_service.repository.TradeOperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradingService {

    private final TradeOperationRepository tradeOperationRepository;
    private final UserService userService;
    private final StockService stockService;

    private static final int MAX_QUANTITY_PER_TRADE = 10_000;
    private static final BigDecimal MIN_TRADE_AMOUNT = new BigDecimal("10.00");
    private static final BigDecimal MIN_PRICE_PER_SHARE = new BigDecimal("0.01");

    /**
     * Ejecutar operación de trading
     */
    @Transactional
    public TradeResponseDTO executeTrade(TradeRequestDTO request) {
        log.info(" Iniciando operación {} de {} acciones de {} a ${}",
                request.getOperationType(), request.getQuantity(),
                request.getSymbol(), request.getPrice());

        validateTradeRequest(request);

        //  CORRECCIÓN: Cambiar findUserEntityById por findUserEntityByUUID
        // Y request.getUserId() ahora devuelve String
        User user = userService.findUserEntityByUUID(request.getUserId());  // ← String
        Stock stock = stockService.findStockBySymbol(request.getSymbol());

        BigDecimal totalAmount = request.getPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        if ("BUY".equals(request.getOperationType())) {
            executeBuy(user, totalAmount);
        } else if ("SELL".equals(request.getOperationType())) {
            executeSell(user, request.getSymbol(), request.getQuantity(), totalAmount);
        }

        TradeOperation operation = TradeOperation.builder()
                .operationNumber(UUID.randomUUID().toString())
                .operationTime(LocalDateTime.now())
                .operationName(request.getOperationType() + " - " + stock.getName())
                .contractQuantity(request.getQuantity())
                .stockPrice(request.getPrice())
                .operationType(request.getOperationType())
                .symbol(request.getSymbol())
                .totalAmount(totalAmount)
                .status("EXECUTED")
                .user(user)
                .build();

        TradeOperation savedOperation = tradeOperationRepository.save(operation);
        log.info(" Operación ejecutada exitosamente. Nuevo balance: ${}", user.getBalance());

        return mapToResponseDTO(savedOperation, user.getBalance());
    }

    /**
     * Obtener historial de operaciones de un usuario
     */
    @Transactional(readOnly = true)
    public List<TradeHistoryDTO> getUserTradeHistory(String userUUID) {  //  String, no Long
        log.debug("📋 Obteniendo historial de operaciones para usuario: {}", userUUID);

        //  CORRECCIÓN: Usar método correcto del repository
        List<TradeOperation> operations = tradeOperationRepository
                .findByUser_UserUUIDOrderByCreatedAtDesc(userUUID);  // ← Método correcto

        return operations.stream()
                .map(this::mapToHistoryDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener cantidad disponible de acciones de un usuario
     */
    @Transactional(readOnly = true)
    public int getAvailableStockQuantity(String userUUID, String symbol) {  //  String, no Long
        List<TradeOperation> operations = tradeOperationRepository
                .findByUser_UserUUIDOrderByCreatedAtDesc(userUUID);  // ← Método correcto

        int netQuantity = operations.stream()
                .filter(op -> symbol.equals(op.getSymbol()))
                .mapToInt(op -> "BUY".equals(op.getOperationType())
                        ? op.getContractQuantity()
                        : -op.getContractQuantity())
                .sum();

        return Math.max(netQuantity, 0);
    }

    /**
     * Ejecutar compra de acciones
     */
    private void executeBuy(User user, BigDecimal totalAmount) {
        if (user.getBalance().compareTo(totalAmount) < 0) {
            log.error(" Fondos insuficientes. Balance: ${}, Requerido: ${}",
                    user.getBalance(), totalAmount);
            throw new IllegalArgumentException(
                    String.format("Fondos insuficientes. Necesitas $%.2f pero solo tienes $%.2f",
                            totalAmount, user.getBalance()));
        }

        BigDecimal newBalance = user.getBalance().subtract(totalAmount);
        //  CORRECCIÓN: user.getUserUUID() ya es String, no necesita Long.valueOf()
        userService.updateUserBalance(user.getUserUUID(), newBalance);  // ← Ya es String
        log.debug(" Compra ejecutada. Nuevo balance: ${}", newBalance);
    }

    /**
     * Ejecutar venta de acciones
     */
    private void executeSell(User user, String symbol, Integer quantity, BigDecimal totalAmount) {
        //  CORRECCIÓN: user.getUserUUID() ya es String
        int ownedQuantity = getAvailableStockQuantity(user.getUserUUID(), symbol);  // ← Ya es String

        if (ownedQuantity < quantity) {
            log.error(" Acciones insuficientes. Tienes: {}, Intentas vender: {}",
                    ownedQuantity, quantity);
            throw new IllegalArgumentException(
                    String.format("No tienes suficientes acciones. Tienes %d pero intentas vender %d",
                            ownedQuantity, quantity));
        }

        BigDecimal newBalance = user.getBalance().add(totalAmount);
        //  CORRECCIÓN: user.getUserUUID() ya es String
        userService.updateUserBalance(user.getUserUUID(), newBalance);  // ← Ya es String
        log.debug(" Venta ejecutada. Nuevo balance: ${}", newBalance);
    }

    /**
     * Validar solicitud de trading
     */
    private void validateTradeRequest(TradeRequestDTO request) {
        if (request.getQuantity() > MAX_QUANTITY_PER_TRADE) {
            throw new IllegalArgumentException(
                    "Cantidad máxima por operación es " + MAX_QUANTITY_PER_TRADE);
        }

        if (request.getPrice().compareTo(MIN_PRICE_PER_SHARE) < 0) {
            throw new IllegalArgumentException(
                    "Precio mínimo por acción es $" + MIN_PRICE_PER_SHARE);
        }

        BigDecimal totalAmount = request.getPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));
        if (totalAmount.compareTo(MIN_TRADE_AMOUNT) < 0) {
            throw new IllegalArgumentException(
                    "Monto mínimo de operación es $" + MIN_TRADE_AMOUNT);
        }
    }

    /**
     * Mapear TradeOperation a TradeResponseDTO
     */
    private TradeResponseDTO mapToResponseDTO(TradeOperation operation, BigDecimal remainingBalance) {
        return TradeResponseDTO.builder()
                .id(operation.getId())
                .operationNumber(operation.getOperationNumber())
                .operationName(operation.getOperationName())
                .operationType(operation.getOperationType())
                .symbol(operation.getSymbol())
                .quantity(operation.getContractQuantity())
                .price(operation.getStockPrice())
                .totalAmount(operation.getTotalAmount())
                .status(operation.getStatus())
                .operationTime(operation.getOperationTime())
                .userUUID(operation.getUser().getUserUUID())  //  CORRECCIÓN: userUUID en lugar de userId
                .remainingBalance(remainingBalance)
                .build();
    }

    /**
     * Mapear TradeOperation a TradeHistoryDTO
     */
    private TradeHistoryDTO mapToHistoryDTO(TradeOperation operation) {
        return TradeHistoryDTO.builder()
                .id(operation.getId())
                .operationNumber(operation.getOperationNumber())
                .operationTime(operation.getOperationTime())
                .operationName(operation.getOperationName())
                .contractQuantity(operation.getContractQuantity())
                .stockPrice(operation.getStockPrice())
                .operationType(operation.getOperationType())
                .symbol(operation.getSymbol())
                .totalAmount(operation.getTotalAmount())
                .status(operation.getStatus())
                .userUUID(operation.getUser().getUserUUID())  //  CORRECCIÓN: userUUID en lugar de userId
                .createdAt(operation.getCreatedAt())
                .build();
    }
}
