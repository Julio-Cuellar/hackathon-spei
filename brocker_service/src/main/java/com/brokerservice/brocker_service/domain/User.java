package com.brokerservice.brocker_service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_uuid", updatable = false, nullable = false, unique = true, length = 36)
    private String userUUID;  // ← PRIMARY KEY - RECIBIDO, NO GENERADO

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(10000.00);

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<TradeOperation> tradeOperations = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ❌ ELIMINAR @PrePersist - Ya no generas UUID automáticamente
    // El UUID DEBE venir como parámetro desde el cliente
}
