package com.donatoordep.debver.services.exceptions;

public class UserExistsInDatabaseException extends RuntimeException{

    private static final String ERROR = "email has exists in database";

    public UserExistsInDatabaseException(){
        super(ERROR);
    }
}
