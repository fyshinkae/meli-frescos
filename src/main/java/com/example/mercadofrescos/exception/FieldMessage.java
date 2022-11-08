package com.example.mercadofrescos.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FieldMessage {
    private String message, field;
}
