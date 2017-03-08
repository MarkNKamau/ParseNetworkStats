package com.marknkamau;

class FileDoesNotExistException extends NoSuchFieldException{
    private static final String DOES_NOT_EXIST = "Input file does not exist.";

    FileDoesNotExistException() {
        super(DOES_NOT_EXIST);
    }
}
