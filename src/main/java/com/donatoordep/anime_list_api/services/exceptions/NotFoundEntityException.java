package com.donatoordep.anime_list_api.services.exceptions;

public class NotFoundEntityException extends RuntimeException{

    private static final String ERROR = "not found in database";

    public NotFoundEntityException(){
        super(ERROR);
    }
}
