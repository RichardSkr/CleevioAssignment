package exceptions;

import exceptions.customExceptions.ProductNameAlreadyExistException;
import exceptions.customExceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ProductNameAlreadyExistException.class, SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<ErrorObject> handleException(Exception ex) {
        String errorMessage = ex instanceof ProductNameAlreadyExistException ?
                "Produkt se stejným názvem již existuje." :
                "Produkt se stejným názvem již existuje.";

        ErrorObject errorObject = ErrorObject.builder()
                .error("Internal Server Error")
                .message(errorMessage)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .path("/api/v1/save/item")
                .build();

        return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorObject> handleValidationException(ValidationException ex) {
        ErrorObject errorObject = ErrorObject.builder()
                .error("Bad Request")
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .path("/api/v1/save/item")
                .build();

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorObject> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorObject errorObject = ErrorObject.builder()
                .error("Bad Request")
                .message("Chybný formát požadavku.")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .path("/api/v1/save/item")
                .build();

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }
}