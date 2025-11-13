package com.cuellar.financialstruments.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "creditos")
@Getter
@Setter
@NoArgsConstructor
public class Credito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, length = 36)
    private String uuid;

    @Column(name = "cuenta_uuid", nullable = false, length = 36)
    private String cuentaUuid;

    @Column(name = "banco", nullable = false)
    private String banco;

    @Column(name = "monto", nullable = false)
    private BigDecimal monto;

    @Column(name = "fecha_corte")
    private LocalDate fechaCorte;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Column(name = "tasa_interes")
    private BigDecimal tasaInteres;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CreditoStatus status;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    public Credito(String uuid, String cuentaUuid, String banco, BigDecimal monto,
                   LocalDate fechaCorte, LocalDate fechaPago, BigDecimal tasaInteres) {
        this.uuid = uuid;
        this.cuentaUuid = cuentaUuid;
        this.banco = banco;
        this.monto = monto;
        this.fechaCorte = fechaCorte;
        this.fechaPago = fechaPago;
        this.tasaInteres = tasaInteres;
        this.status = CreditoStatus.ACTIVO;
        this.fechaCreacion = LocalDateTime.now();
    }
}