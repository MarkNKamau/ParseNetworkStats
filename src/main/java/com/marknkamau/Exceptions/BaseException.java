package com.marknkamau.Exceptions;

public class BaseException extends Exception {
    private final String title;
    private final String message;

    public BaseException(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
