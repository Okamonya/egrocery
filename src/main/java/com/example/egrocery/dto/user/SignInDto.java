package com.example.egrocery.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {

    private String userName;
    private String email;
    private  String password;
}
