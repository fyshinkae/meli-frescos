package com.example.mercadofrescos.exception;


public class ProductsListNotFoundException extends RuntimeException {
    public ProductsListNotFoundException(String message) {
        super(message);
    }
}
