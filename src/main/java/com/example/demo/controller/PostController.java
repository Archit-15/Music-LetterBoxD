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

import com.example.demo.model.Post;
import com.example.demo.service.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
    
    private PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService =postService;
    }

    @PutMapping("/approve-post/{postId}")
    public ResponseEntity<Post> approvePost(@PathVariable long postId){
        
        Post approvedPost = postService.approvePost(postId);

        if(approvedPost != null){
            return ResponseEntity.status(200).body(approvedPost);
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/disapprove-post/{postId}")
    public ResponseEntity<Boolean> disapprovePost(@PathVariable long postId){
        
        boolean disapprovedPostVal = postService.disapprovePost(postId);

        if(disapprovedPostVal){
            return ResponseEntity.status(200).body(disapprovedPostVal);
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping("/approved")
    public ResponseEntity<List<Post>> getApprovedPosts(){
        
        List<Post> approved = postService.getAllApprovedPosts();

        if(approved != null){
            return ResponseEntity.status(200).body(approved);
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping("/unapproved")
    public ResponseEntity<List<Post>> getUnapprovedPosts(){
        
        List<Post> unapproved = postService.getAllUnapprovedPosts();

        if(unapproved != null){
            return ResponseEntity.status(200).body(unapproved);
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable long userId){
        
        List<Post> postsByUser = postService.getApprovedPostsByUserId(userId);

        if(postsByUser!= null){
            return ResponseEntity.status(200).body(postsByUser);
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping("/unapproved/{userId}")
    public ResponseEntity<List<Post>> getUnapprovedPostsByUserId(@PathVariable long userId){
        
        List<Post> postsByUser = postService.getDisapprovedPostsByUserId(userId);

        if(postsByUser!= null){
            return ResponseEntity.status(200).body(postsByUser);
        }

        return ResponseEntity.status(404).build();
    }



    @PostMapping("/create/{userId}")
    public ResponseEntity<Post> createPost(@RequestBody Post post , @PathVariable long userId){
        
        Post createdPost = postService.createPost(userId, post);

        if(createdPost != null){
            return ResponseEntity.status(200).body(createdPost);
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Boolean> deletePost(@PathVariable long postId){
        
        boolean delVal = postService.deletePost(postId);

        if(delVal){
            return ResponseEntity.status(204).body(true);
        }

        return ResponseEntity.status(404).body(false);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<Post> updatePost(@RequestBody Post post , @PathVariable long postId){
        
        Post updatedPost = postService.updatePost(postId, post);

        if(updatedPost != null){
            return ResponseEntity.status(200).body(updatedPost);
        }

        return ResponseEntity.status(404).build();

    }


    @PutMapping("/like/{userId}/{postId}")
    public ResponseEntity<Long> likePost(@PathVariable long userId, @PathVariable long postId){
        
        long likes = postService.likePost(postId , userId);

        if(likes < 0){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(likes);

    }


    @PutMapping("/dislike/{userId}/{postId}")
    public ResponseEntity<Long> dislikePost(@PathVariable long userId, @PathVariable long postId){
        
        long likes = postService.unlikePost(postId, userId);

        if(likes < 0){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(likes);

    }


    @GetMapping("likes/{postId}")
    public ResponseEntity<Long> getLikes(@PathVariable long postId){
        
        long likes = postService.getLikes(postId);

        if(likes < 0){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(likes);

    }



}
