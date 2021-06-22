package com.example.app_dev.service.exception;

public class NotFoundPageException extends RuntimeException{
    public NotFoundPageException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
