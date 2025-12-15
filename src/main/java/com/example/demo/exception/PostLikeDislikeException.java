package com.example.demo.exception;


public class PostLikeDislikeException extends RuntimeException {

    private int status;

    public PostLikeDislikeException(int status, String message){
        super(message);
        this.status = status;
    }

    public int getStatus(){
        return this.status;
    }
    
}
