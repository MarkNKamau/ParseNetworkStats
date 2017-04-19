package com.marknkamau.Exceptions;

public class FileDoesNotExistException extends MyExceptions{
    private static final String TITLE = "File does not exist";
    private static final String DOES_NOT_EXIST = "Input file does not exist.";

    public FileDoesNotExistException() {
        super(TITLE, DOES_NOT_EXIST);
    }
}
