package com.example.demo.exception;

public class AlreadyFriendsException extends RuntimeException {
    
    private int status;

    public AlreadyFriendsException(int status, String message){
        super(message);
        this.status = status;
    }

    public int getStatus(){
        return this.status;
    }

}
