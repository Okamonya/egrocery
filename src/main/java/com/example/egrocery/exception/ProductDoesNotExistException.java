package com.example.egrocery.exception;

public class ProductDoesNotExistException extends Throwable {
    public ProductDoesNotExistException(String msg) {
        super(msg);
    }
}
