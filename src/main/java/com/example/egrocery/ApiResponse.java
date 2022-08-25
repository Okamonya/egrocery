package com.example.egrocery;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ApiResponse {

    private final Boolean success;
    private final String message;

    public String getTimeStamp() {
        return LocalDateTime.now().toString();
    }
}
