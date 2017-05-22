package com.marknkamau.Exceptions;

public class NoOutputSelected extends BaseException {
    private static final String TITLE = "No output selected";
    private static final String MESSAGE = "At least one output is required";
    public NoOutputSelected() {
        super(TITLE, MESSAGE);
    }
}
