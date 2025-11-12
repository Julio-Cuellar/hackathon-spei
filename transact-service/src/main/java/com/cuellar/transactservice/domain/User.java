package com.cuellar.transactservice.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "accounts",
        indexes = {
                @Index(name = "idx_accounts_user_uuid", columnList = "user_uuid", unique = true),
                @Index(name = "idx_accounts_cuenta_clave", columnList = "cuenta_clave", unique = true)
        })
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_uuid", unique = true, nullable = false, length = 36)
    private String userUUID;

    @Column(name = "cuenta_clave", nullable = false, unique = true, length = 18)
    private String cuentaClave;

    @Column(name = "saldo", nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "activa", nullable = false)
    @Builder.Default
    private Boolean activa = true;

    @PrePersist
    public void prePersist() {
        if (this.userUUID == null) this.userUUID = UUID.randomUUID().toString();
        if (this.cuentaClave == null) this.cuentaClave = generateCuentaClave();
        if (this.fechaCreacion == null) this.fechaCreacion = LocalDateTime.now();
        if (this.saldo == null) this.saldo = BigDecimal.ZERO;
        if (this.activa == null) this.activa = true;
    }

    private String generateCuentaClave() {
        long clabe = (long) (Math.random() * 900000000000000000L) + 100000000000000000L;
        return String.valueOf(clabe);
    }
}
