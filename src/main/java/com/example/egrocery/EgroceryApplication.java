package com.example.egrocery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class EgroceryApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgroceryApplication.class, args);
    }

}
