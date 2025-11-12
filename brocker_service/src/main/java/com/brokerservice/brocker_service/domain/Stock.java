package com.brokerservice.brocker_service.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stocks") // Cambié TableGenerator por Table
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol", unique = true, nullable = false)
    private String symbol;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "current_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal currentPrice;

    @Column(name = "currency", nullable = false)
    @Builder.Default
    private String currency = "USD";

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}