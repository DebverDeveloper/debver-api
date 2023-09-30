package com.donatoordep.debver.services.exceptions;

public class EntityNotAuthenticatedInSystemException extends RuntimeException{

    private static final String ERROR = "user not authenticate";

    public EntityNotAuthenticatedInSystemException(){
        super(ERROR);
    }
}
