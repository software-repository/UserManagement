package com.jas.exceptions;

public class DepartmentAlreadyExistsException extends RuntimeException{
    public DepartmentAlreadyExistsException(String s)
    {
        super(s);
    }
}
