package com.marknkamau.Exceptions;

public class InputExtensionException extends MyExceptions {
    private static final String TITLE = "Incompatible input file";
    private static final String INPUT_EXTENSION_MESSAGE = "Input file must be a plain text file (.txt)";

    public InputExtensionException() {
        super(TITLE, INPUT_EXTENSION_MESSAGE);
    }
}
