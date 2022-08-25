package com.example.egrocery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SignInResponseDto {

    private String message;
    private String token;
}
