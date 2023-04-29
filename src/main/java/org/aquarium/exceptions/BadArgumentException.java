package org.aquarium.exceptions;

public class BadArgumentException extends Exception{
    public BadArgumentException(){}
    public BadArgumentException(String message){
        super(message);
    }
    public  BadArgumentException(Throwable cause){
        super(cause);
    }
    public BadArgumentException(String message, Throwable cause){
        super(message, cause);
    }
}
