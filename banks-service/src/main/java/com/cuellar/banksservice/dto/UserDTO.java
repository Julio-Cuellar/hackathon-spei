package com.cuellar.banksservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    public Long id;

    private String userUUID;

    public String name;

    public String lastName;

    private String bankName;

    private String numeroCuenta;

    private String CCV;

    private String fechaVencimiento;

    private String CLABE;


}
