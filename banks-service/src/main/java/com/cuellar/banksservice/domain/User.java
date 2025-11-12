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
    @Column(name = "UUID", nullable = true)
    private String UUID;

    @Column(name = "name")
    private String name;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "bankName")
    private String bankName;

    @Column(name = "numeroCuenta")
    private String numeroCuenta;

    @Column(name = "cuentaClabe")
    private String cuentaClabe;

    @Column(name = "monto")
    private String monto;
}
