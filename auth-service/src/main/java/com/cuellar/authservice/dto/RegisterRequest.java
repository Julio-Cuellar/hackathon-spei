package com.cuellar.authservice.dto;

import lombok.Data;

@Data
public class RegisterRequest {


    public String name;

    public String username;

    private String email;

    private String password;




}
