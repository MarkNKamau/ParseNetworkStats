package com.marknkamau;

class InputMissingExtensionException extends Exception {

    private static final String MISSING_INPUT_EXTENSION = "Input file does not have an extension.";

    InputMissingExtensionException() {
        super(MISSING_INPUT_EXTENSION);
    }
}
