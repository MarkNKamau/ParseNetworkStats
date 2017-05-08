package com.marknkamau.Exceptions;

public class FileDoesNotExist extends BaseException {
    private static final String TITLE = "File does not exist";
    private static final String DOES_NOT_EXIST = "Input file does not exist.";

    public FileDoesNotExist() {
        super(TITLE, DOES_NOT_EXIST);
    }
}
