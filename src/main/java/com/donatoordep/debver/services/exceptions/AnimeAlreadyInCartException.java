package com.donatoordep.debver.services.exceptions;

public class AnimeAlreadyInCartException extends RuntimeException {

    private static final String ERROR = "anime already in cart";

    public AnimeAlreadyInCartException(Long id) {
        super(String.format("%s: %d", ERROR, id));
    }
}
