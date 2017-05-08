package com.marknkamau.Exceptions;

public class IncorrectInputExtension extends BaseException {
    private static final String TITLE = "Incompatible input file";
    private static final String INPUT_EXTENSION_MESSAGE = "Input file must be a plain text file (.txt)";

    public IncorrectInputExtension() {
        super(TITLE, INPUT_EXTENSION_MESSAGE);
    }
}
