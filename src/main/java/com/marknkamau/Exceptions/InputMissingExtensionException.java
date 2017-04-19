package com.marknkamau.Exceptions;

public class InputMissingExtensionException extends MyExceptions {
    private static final String TITLE = "File missing extention";
    private static final String MISSING_INPUT_EXTENSION = "Input file does not have an extension.";

    public InputMissingExtensionException() {
        super(TITLE, MISSING_INPUT_EXTENSION);
    }
}
