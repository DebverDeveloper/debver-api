package com.donatoordep.anime_list_api.services.exceptions;

public class CodeNotValidException extends RuntimeException {

    private static final String ERROR = "your code is not valid";

    public CodeNotValidException() {
        super(ERROR);
    }
}
