package com.brokerservice.brocker_service.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trade_operations")
public class TradeOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operationNumber;
    private LocalDateTime operationTime;
    private String operationName;
    private Integer contractQuantity;
    private BigDecimal stockPrice;
    private String operationType; // BUY or SELL
    private String symbol;
    private BigDecimal totalAmount;

    @Builder.Default
    private String status = "EXECUTED";

    // ✅ SOLO la relación con User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.operationTime == null) {
            this.operationTime = LocalDateTime.now();
        }
        if (this.operationNumber == null) {
            this.operationNumber = "OP-" + System.currentTimeMillis();
        }
        if (this.totalAmount == null && this.stockPrice != null && this.contractQuantity != null) {
            this.totalAmount = this.stockPrice.multiply(BigDecimal.valueOf(this.contractQuantity));
        }
    }

    // ✅ Getter para userId
    public Long getUserId() {
        return this.user != null ? this.user.getId() : null;
    }
}