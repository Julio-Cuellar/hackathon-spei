package com.cuellar.financialstruments.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hipotecas")
@Getter
@Setter
@NoArgsConstructor
public class Hipoteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, length = 36)
    private String uuid;

    @Column(name = "cuenta_uuid", nullable = false, length = 36)
    private String cuentaUuid;

    @Column(name = "banco")
    private String banco;

    @Column(name = "monto")
    private BigDecimal monto;

    @Column(name = "plazo_meses")
    private Integer plazoMeses;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    public Hipoteca(String uuid, String cuentaUuid, String banco, BigDecimal monto, Integer plazoMeses) {
        this.uuid = uuid;
        this.cuentaUuid = cuentaUuid;
        this.banco = banco;
        this.monto = monto;
        this.plazoMeses = plazoMeses;
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
    }
}