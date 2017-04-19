package com.marknkamau.Exceptions;

public class EmptyInputFileException extends MyExceptions {
    private static final String TITLE = "Empty input file";
    private static final String EMPTY_INPUT_FILE = "Input file is empty.";

    public EmptyInputFileException() {
        super(TITLE, EMPTY_INPUT_FILE);
    }
}
