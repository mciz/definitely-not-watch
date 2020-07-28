package com.watches.exception;

import com.watches.validation.ApiValidationError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(TitleAlreadyExistsException.class)
    public final ResponseEntity<?> handleConflictExceptions(Exception ex) {
        ApiValidationError apiValidationError = new ApiValidationError(CONFLICT, "Title already exists");
        return buildResponseEntity(apiValidationError);
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ApiValidationError apiValidationError = new ApiValidationError();
        Optional<ObjectError> error = ex.getBindingResult().getAllErrors().stream().findFirst();
        error.ifPresent(
                e -> {
                    apiValidationError.setStatus(BAD_REQUEST);
                    apiValidationError.setMessage(e.getDefaultMessage());
                }
        );
        return buildResponseEntity(apiValidationError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiValidationError apiValidationError) {
        return new ResponseEntity<>(apiValidationError, apiValidationError.getStatus());
    }
}
