package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;


@RestController
@RequestMapping("/api/comment")
public class CommentController {
    
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentByPostId(@PathVariable long postId){

        List<Comment> comments = commentService.getCommentByPostId(postId);

        if(comments != null){
            return ResponseEntity.status(200).body(comments);
        }

        return ResponseEntity.status(404).build();

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getCommentByUserId(@PathVariable long userId){

        List<Comment> comments = commentService.getCommentsByUserId(userId);

        if(comments != null){
            return ResponseEntity.status(200).body(comments);
        }

        return ResponseEntity.status(404).build();

    }


    @GetMapping("likes/{commentId}")
    public ResponseEntity<Long> getLikes(@PathVariable long commentId){
        
        long likes = commentService.getLikes(commentId);

        if(likes < 0){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(likes);

    }

    @PostMapping("/create/{userId}/{postId}")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @PathVariable long userId, @PathVariable long postId){
        
        Comment createdComment = commentService.createComment(userId, postId, comment);

        if(createdComment != null){
            return ResponseEntity.status(201).body(createdComment);
        }

        return ResponseEntity.status(500).build();

    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable long commentId){
        
        boolean deleteVal = commentService.deleteComment(commentId);

        if(deleteVal){
            return ResponseEntity.status(204).body(true);
        }

        return ResponseEntity.status(404).body(false);

    }


    @PutMapping("/update/{userId}/{commentId}")
    public ResponseEntity<Comment> updateComment(@RequestBody Comment newComment , @PathVariable long commentId, @PathVariable long userId){
        
        Comment updatedComment = commentService.updateComment(userId, commentId, newComment);

        if(updatedComment != null){
            return ResponseEntity.status(200).body(updatedComment);
        }

        return ResponseEntity.status(404).build();

    }

    
@PutMapping("/like/{userId}/{commentId}")
    public ResponseEntity<Long> likeComment(@PathVariable long userId, @PathVariable long commentId){
        
        long likes = commentService.likeComment(commentId, userId);

        if(likes < 0){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(likes);

    }


    @PutMapping("/dislike/{userId}/{commentId}")
    public ResponseEntity<Long> dislikeComment(@PathVariable long userId, @PathVariable long commentId){
        
        long likes =  commentService.dislikeComment(commentId, userId);

        if(likes < 0){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(likes);

    }



}
