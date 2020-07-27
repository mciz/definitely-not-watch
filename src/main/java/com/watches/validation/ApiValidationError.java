package com.watches.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiValidationError extends Error {

    private String field;
    private Object rejectedValue;
    private String message;

    public ApiValidationError(String message) {
        this.message = message;
    }
}
