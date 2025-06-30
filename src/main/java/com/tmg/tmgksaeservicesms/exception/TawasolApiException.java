package com.tmg.tmgksaeservicesms.exception;

public class TawasolApiException extends RuntimeException {

    public TawasolApiException(String message) {
        super(message);
    }

    public TawasolApiException(String message, Throwable cause) {
        super(message, cause);
    }
}