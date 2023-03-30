package com.gini.error.error;


public record ErrorResponse(
        int status,
        String error,
        String errorMessages
) {
}
