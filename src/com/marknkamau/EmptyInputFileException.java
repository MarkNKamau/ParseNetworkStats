package com.marknkamau;

class EmptyInputFileException extends Exception {
    private static final String EMPTY_INPUT_FILE = "Input file is empty.";

    EmptyInputFileException() {
        super(EMPTY_INPUT_FILE);
    }
}
