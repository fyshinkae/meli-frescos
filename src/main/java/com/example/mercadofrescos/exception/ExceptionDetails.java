package com.example.mercadofrescos.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ExceptionDetails {
    private String title, message;
    private int status;
    private List<FieldMessage> fieldsMessage;
    private LocalDateTime timestamps;
}
