package com.cuellar.banksservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {

    @JsonProperty("UUID")
    private String UUID;
    private String name;
    private String lastName;
    private String bankName;
    private String numeroCuenta;
    private String cuentaClabe;
}
