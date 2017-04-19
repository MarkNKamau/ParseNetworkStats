package com.marknkamau.Exceptions;

public class MyExceptions extends Exception {
    private final String title;
    private final String message;

    public MyExceptions(String title, String message) {
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
