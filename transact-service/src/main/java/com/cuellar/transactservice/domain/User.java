package com.cuellar.transactservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "uuid", nullable = false, unique = true, length = 36)
    private String userUUID;

    @Column(name = "cuenta_clabe", unique = true, length = 18)
    private String cuentaClabe;

    @Column(name = "saldo", nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "activa", nullable = false)
    @Builder.Default
    private Boolean activa = true;

    // Transacciones donde este usuario es el origen
    @OneToMany(mappedBy = "cuentaOrigenUser", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Transaction> transaccionesOrigen = new ArrayList<>();

    // Transacciones donde este usuario es el destino
    @OneToMany(mappedBy = "cuentaDestinoUser", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Transaction> transaccionesDestino = new ArrayList<>();

    @PrePersist
    public void generateIdentifiers() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
    }
}
