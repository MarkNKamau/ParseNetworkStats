package com.marknkamau;

class InputExtensionException extends Exception {
    private static final String INPUT_EXTENSION_MESSAGE = "Input file must be a plain text file (.txt)";

    InputExtensionException() {
        super(INPUT_EXTENSION_MESSAGE);
    }
}
