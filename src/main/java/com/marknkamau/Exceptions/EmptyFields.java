package com.marknkamau.Exceptions;

public class EmptyFields extends BaseException {
    private static final String TITLE = "Empty fields";
    private static final String EMPTY_FIELDS = "All fields are required";

    public EmptyFields() {
        super(TITLE, EMPTY_FIELDS);
    }
}
