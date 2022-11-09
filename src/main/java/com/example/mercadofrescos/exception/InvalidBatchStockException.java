package com.example.mercadofrescos.exception;

public class InvalidBatchStockException extends RuntimeException {
    public InvalidBatchStockException(String message) {
        super(message);
    }
}
