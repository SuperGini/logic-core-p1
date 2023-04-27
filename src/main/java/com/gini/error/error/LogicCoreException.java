package com.gini.error.error;

public class LogicCoreException extends RuntimeException {

    public LogicCoreException(String message) {
        super(message);
    }

    public LogicCoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
