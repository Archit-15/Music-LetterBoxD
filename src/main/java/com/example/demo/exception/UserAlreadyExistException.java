package com.example.demo.exception;

public class UserAlreadyExistException extends RuntimeException{
    
    private int status;
    
    public UserAlreadyExistException(int status , String message){
        super(message);
        this.status = status;
    }

    int getStatus(){
        return this.status;
    }

}
