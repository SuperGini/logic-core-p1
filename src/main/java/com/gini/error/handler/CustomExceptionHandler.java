package com.gini.error.handler;

import com.gini.error.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * https://stackoverflow.com/questions/23699371/java-8-distinct-by-property
     * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidExceptionException(MethodArgumentNotValidException e) {

        var fieldErrors = e.getBindingResult().getFieldErrors();

        var fieldsWithErrors = fieldErrors.stream()
                                                    .filter(distinctByKey(FieldError::getField))
                                                    .map(this::fieldsWithErrors)
                                                    .collect(Collectors.joining("; "));

        return new ErrorResponse(
                                400,
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                fieldsWithErrors
        );

    }


    public <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        final Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private String fieldsWithErrors(FieldError fieldError) {
        String fieldName = fieldError.getField();
        String errorMessage = fieldError.getDefaultMessage();

        if (errorMessage.contains(fieldName)) {
            return errorMessage;
        }

        return fieldName + " " + errorMessage;
    }

}
