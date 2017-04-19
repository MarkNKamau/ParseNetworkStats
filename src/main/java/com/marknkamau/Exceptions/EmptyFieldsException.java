package com.marknkamau.Exceptions;

public class EmptyFieldsException extends MyExceptions{
    private static final String TITLE = "Empty fields";
    private static final String EMPTY_FIELDS = "All fields are required";

    public EmptyFieldsException() {
        super(TITLE, EMPTY_FIELDS);
    }
}
