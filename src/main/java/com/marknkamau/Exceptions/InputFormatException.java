package com.marknkamau.Exceptions;

public class InputFormatException extends MyExceptions {
    private static final String TITLE = "Input format error";
    private static final String ILLEGAL_CONTENT_FORMAT = "Some of the input was not formatted correctly.\n\nEnsure input is in the format of:\n\n" +
            "Ping statistics for x.x.x.x:\n" +
            "    Packets: Sent = x, Received = x, Lost = x (x% loss),\n" +
            "Approximate round trip times in milli-seconds:\n" +
            "    Minimum = x ms, Maximum = x ms, Average = x ms";

    public InputFormatException() {
        super(TITLE, ILLEGAL_CONTENT_FORMAT);
    }
}
