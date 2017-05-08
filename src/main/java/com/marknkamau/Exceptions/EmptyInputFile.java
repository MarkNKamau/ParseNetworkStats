package com.marknkamau.Exceptions;

public class EmptyInputFile extends BaseException {
    private static final String TITLE = "Empty input file";
    private static final String EMPTY_INPUT_FILE = "Input file is empty.";

    public EmptyInputFile() {
        super(TITLE, EMPTY_INPUT_FILE);
    }
}
