package com.donatoordep.debver.services.exceptions;

public class WeakPasswordException extends RuntimeException{
    private static final String ERROR = "Weak password, minimum is 5 characters";

    public WeakPasswordException(){
        super(ERROR);
    }
}
