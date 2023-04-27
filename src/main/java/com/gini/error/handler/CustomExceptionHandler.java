package com.gini.error.handler;

import com.gini.error.error.InvalidCredentialsException;
import com.gini.error.error.LogicCoreException;
import com.gini.error.error.UserAlreadyExists;
import com.gini.error.error.NotFoundException;
import com.gini.error.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
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
        log.error("Validation error: ", e);
        return new ErrorResponse(
                                400,
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                fieldsWithErrors
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e){
        log.error("Exception is: {}", e.getMessage(), e);
        return new ErrorResponse(
                404,
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage()
        );
    }

    @ExceptionHandler(UserAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUserAlreadyExistException(UserAlreadyExists e){
        log.error("User already exists: ", e);
        return new ErrorResponse(
                409,
                HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage()
        );
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e){
        log.error("Invalid username: ", e);
        return new ErrorResponse(
                400,
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidCredentialsException(InvalidCredentialsException e){
        log.error("Invalid credentials: ", e);
        return new ErrorResponse(
                400,
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        log.error("User id not found: ", e);
        return new ErrorResponse(
                400,
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Could not create folder, wrong userId");
    }

    @ExceptionHandler(LogicCoreException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleLogicCoreException(LogicCoreException e){
        log.error("Exception is: {}", e.getMessage(), e);
        return new ErrorResponse(
                400,
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e){
        log.error("Exception is: {}", e.getMessage(), e);
        return new ErrorResponse(
                500,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Shit happens -> :( ");
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
