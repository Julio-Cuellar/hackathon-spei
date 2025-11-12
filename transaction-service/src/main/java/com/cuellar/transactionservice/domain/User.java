package com.cuellar.transactionservice.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, length = 36)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String uuid;

    @Column(name = "cuenta_clave", nullable = false, unique = true, length = 18)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String cuentaClave;

    @Column(name = "saldo", nullable = false, precision = 19, scale = 2)
    @ToString.Include
    @Builder.Default
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "fecha_creacion", nullable = false)
    @ToString.Include
    private LocalDateTime fechaCreacion;

    @Column(name = "activa", nullable = false)
    @ToString.Include
    @Builder.Default
    private Boolean activa = true;

    @OneToMany(mappedBy = "cuentaOrigenAccount", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Transaction> transaccionesOrigen = new ArrayList<>();

    @OneToMany(mappedBy = "cuentaDestinoAccount", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Transaction> transaccionesDestino = new ArrayList<>();

    @PrePersist
    public void generateIdentifiers() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
        if (this.cuentaClave == null) {
            this.cuentaClave = generateCuentaClave();
        }
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
        if (this.saldo == null) {
            this.saldo = BigDecimal.ZERO;
        }
        if (this.activa == null) {
            this.activa = true;
        }
    }

    private String generateCuentaClave() {
        long clabe = (long) (Math.random() * 900000000000000000L) + 100000000000000000L;
        return String.valueOf(clabe);
    }
}
