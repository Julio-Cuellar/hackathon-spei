package com.cuellar.banksservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userUUID;

    private String name;

    private String lastName;

    private String bankName;

    private String numeroCuenta;

    private String CCV;

    private String fechaVencimiento;

    private String CLABE;

    private String monto;


}
