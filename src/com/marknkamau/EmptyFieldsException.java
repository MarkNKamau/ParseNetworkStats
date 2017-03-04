package com.marknkamau;

class EmptyFieldsException extends Exception{
    private static final String EMPTY_FIELDS = "All fields are required";
    EmptyFieldsException() {
        super(EMPTY_FIELDS);
    }
}
