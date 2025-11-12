package com.cuellar.transactservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String userUUID;
    private String cuentaClabe;
    private BigDecimal saldo;
    private LocalDateTime fechaCreacion;
    private Boolean activa;

}
