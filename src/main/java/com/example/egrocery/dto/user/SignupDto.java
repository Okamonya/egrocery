package com.example.egrocery.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
}
