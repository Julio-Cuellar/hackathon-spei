package com.cuellar.financialstruments.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "seguros")
@Getter
@Setter
@NoArgsConstructor
public class Seguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, length = 36)
    private String uuid;

    @Column(name = "cuenta_uuid", nullable = false, length = 36)
    private String cuentaUuid;

    @Column(name = "tipo_seguro")
    private String tipoSeguro;

    @Column(name = "aseguradora")
    private String aseguradora;

    @Column(name = "numero_poliza")
    private String numeroPoliza;

    @Column(name = "suma_asegurada")
    private BigDecimal sumaAsegurada;

    @Column(name = "prima_mensual")
    private BigDecimal primaMensual;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    public Seguro(String uuid, String cuentaUuid, String tipoSeguro, String aseguradora,
                  String numeroPoliza, BigDecimal sumaAsegurada, BigDecimal primaMensual,
                  LocalDate fechaInicio, LocalDate fechaVencimiento) {
        this.uuid = uuid;
        this.cuentaUuid = cuentaUuid;
        this.tipoSeguro = tipoSeguro;
        this.aseguradora = aseguradora;
        this.numeroPoliza = numeroPoliza;
        this.sumaAsegurada = sumaAsegurada;
        this.primaMensual = primaMensual;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
    }
}