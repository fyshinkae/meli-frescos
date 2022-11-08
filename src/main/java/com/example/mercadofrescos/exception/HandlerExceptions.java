package com.example.mercadofrescos.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandlerExceptions extends ResponseEntityExceptionHandler {

    /**
     * Monta uma lista de erros com a "field" correspondente com o seu erro específico
     * @author Theus
     * @param errors uma lista de FieldErros
     */
    private List<FieldMessage> mountFieldErrorMessage(List<FieldError> errors) {
        return errors.stream()
                .map(error -> FieldMessage.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .build()
                ).collect(Collectors.toList());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();

        ExceptionDetails details = ExceptionDetails.builder()
                .title("Invalid values")
                .message("Some values are invalid")
                .fieldsMessage(this.mountFieldErrorMessage(errors))
                .status(status.value())
                .timestamps(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(details, status);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerNotFoundException(NotFoundException ex) {
        ExceptionDetails details = ExceptionDetails.builder()
                .title("Resource not found")
                .message(ex.getMessage())
                .timestamps(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }
}
