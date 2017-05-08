package com.marknkamau.Exceptions;

public class InputFileMissingExtension extends BaseException {
    private static final String TITLE = "File missing extention";
    private static final String MISSING_INPUT_EXTENSION = "Input file does not have an extension.";

    public InputFileMissingExtension() {
        super(TITLE, MISSING_INPUT_EXTENSION);
    }
}
