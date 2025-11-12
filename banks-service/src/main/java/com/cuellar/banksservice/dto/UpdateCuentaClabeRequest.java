package com.cuellar.banksservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCuentaClabeRequest {

    @NotBlank(message = "La cuenta CLABE es obligatoria")
    @Size(min = 18, max = 18, message = "La cuenta CLABE debe tener exactamente 18 dígitos")
    @Pattern(regexp = "^[0-9]{18}$", message = "La cuenta CLABE debe contener solo números")
    private String cuentaClabe;

    @NotBlank(message = "El nombre del banco es obligatorio")
    @Size(max = 100, message = "El nombre del banco no puede exceder 100 caracteres")
    private String bankName;

    @Size(max = 20, message = "El número de cuenta no puede exceder 20 caracteres")
    private String numeroCuenta;
}
