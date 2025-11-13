package com.brokerservice.brocker_service.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trade_operations", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_symbol", columnList = "symbol"),
        @Index(name = "idx_operation_time", columnList = "operation_time")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String operationNumber;

    @Column(nullable = false)
    private LocalDateTime operationTime;

    @Column(nullable = false, length = 100)
    private String operationName;

    @Column(nullable = false)
    private Integer contractQuantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal stockPrice;

    @Column(nullable = false, length = 10)
    private String operationType; // BUY or SELL

    @Column(nullable = false, length = 10)
    private String symbol;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "EXECUTED";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.operationTime == null) {
            this.operationTime = LocalDateTime.now();
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
