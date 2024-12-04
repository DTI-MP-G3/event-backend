package com.event.event.common.exception;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String msg){
        super(msg);
    }
}
