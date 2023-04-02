package com.gini.error.response;


public record ErrorResponse(
        int status,
        String error,
        String errorMessages
) {
}
