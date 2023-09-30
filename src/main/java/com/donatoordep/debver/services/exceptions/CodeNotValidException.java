package com.donatoordep.debver.services.exceptions;

public class CodeNotValidException extends RuntimeException {

    private static final String ERROR = "your code is not valid";

    public CodeNotValidException() {
        super(ERROR);
    }
}
