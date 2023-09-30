package com.donatoordep.debver.services.exceptions;

public class NotFoundEntityException extends RuntimeException{

    private static final String ERROR = "not found in database";

    public NotFoundEntityException(){
        super(ERROR);
    }
}
