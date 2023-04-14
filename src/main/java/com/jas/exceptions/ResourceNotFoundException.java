package com.jas.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String s)
    {
        super(s);
    }
}
