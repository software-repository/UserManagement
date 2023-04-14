package com.jas.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String s)
    {
        super(s);
    }
}
