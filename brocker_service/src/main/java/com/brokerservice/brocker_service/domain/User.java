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
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ✅ Primer parámetro: ID numérico

    @Column(unique = true, nullable = false)
    private String uuid; // ✅ Segundo parámetro: UUID como String

    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(10000.00);

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    @JsonIgnore
    private List<TradeOperation> tradeOperations = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ✅ Método para generar UUID automáticamente antes de persistir
    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

    // Métodos de negocio
    public void addTradeOperation(TradeOperation operation) {
        operation.setUser(this);
        this.tradeOperations.add(operation);
    }

    public boolean hasSufficientBalance(BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }

    public void deductBalance(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }

    public void addBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }
}