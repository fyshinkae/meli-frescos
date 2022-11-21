package com.example.mercadofrescos.exception;

public class InvalidStatusOrderCancel extends RuntimeException {
    public InvalidStatusOrderCancel(String message) {
        super(message);
    }
}
