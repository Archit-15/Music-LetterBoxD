package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
 
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException ue){
        return ResponseEntity.status(ue.getStatus()).body(ue.getMessage());
    }

    @ExceptionHandler(PostLikeDislikeException.class)
    public ResponseEntity<String> handlePostAlreadyLikedException(PostLikeDislikeException pl){
        return ResponseEntity.status(pl.getStatus()).body(pl.getMessage());
    }


    @ExceptionHandler(AlreadyFriendsException .class)
    public ResponseEntity<String> handleAlreadyFriendsException(AlreadyFriendsException af){
        return ResponseEntity.status(af.getStatus()).body(af.getMessage());
    }


}
