package com.event.event.common.exception;

public class InsufficientTicketsException extends  RuntimeException{

    public InsufficientTicketsException(String msg){
        super(msg);
    }
}
