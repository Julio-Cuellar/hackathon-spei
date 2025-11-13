package com.cuellar.financialstruments.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateSeguroRequest {
    private String uuid;
    private String cuentaUuid;
    private String tipoSeguro;
    private String aseguradora;
    private String numeroPoliza;
    private BigDecimal sumaAsegurada;
    private BigDecimal primaMensual;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
}