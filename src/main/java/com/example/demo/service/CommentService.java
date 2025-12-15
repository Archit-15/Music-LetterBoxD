package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.Users;
import com.example.demo.repository.CommentRepo;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.UsersRepo;

@Service
public class CommentService {

    private CommentRepo commentRepo;
    private UsersRepo usersRepo;
    private PostRepo postRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo , UsersRepo usersRepo , PostRepo postRepo){
        this.commentRepo = commentRepo;
        this.usersRepo = usersRepo;
        this.postRepo = postRepo;
    }

    public Comment createComment(long userId ,long postId , Comment comment){
        Optional<Users> user = usersRepo.findById(userId);
        Optional<Post> post = postRepo.findById(postId);
        
        if (user.isPresent() && post.isPresent() ) {
            comment.setUser(user.get());
            comment.setPost(post.get());
            user.get().getCommentsMade().add(comment);
            post.get().getComments().add(comment);

            usersRepo.save(user.get());
            postRepo.save(post.get());
            return commentRepo.save(comment);
        }

        return null;
    }

    public boolean deleteComment(long commentId){
        Optional<Comment> comment = commentRepo.findById(commentId);

        if(comment.isPresent()){
            //This is not necessary as these are foreign keys an deleting just comment is enough for synchronization
            // Users user = comment.get().getUser();
            // user.getCommentsMade().remove(comment.get());
            // usersRepo.save(user);

            // Post post = comment.get().getPost();
            // post.getComments().remove(comment.get());
            // postRepo.save(post);


            for(Users user : comment.get().getLikedUsers()){
                user.getLikedComments().remove(comment.get());
            }
            comment.get().getLikedUsers().clear();

            commentRepo.delete(comment.get());
            return true;
        }

        return false;
    }

    public Comment updateComment(long userId, long commentId , Comment newComment){
        Optional<Comment> comment = commentRepo.findById(commentId);
        
        if(comment.isPresent() && comment.get().getUser().getId() == userId){
            comment.get().setCommentText(newComment.getCommentText());
            return commentRepo.save(comment.get());
        }

        return null;
    }

    public long likeComment(long commentId , long userId){
        Optional<Comment> comment = commentRepo.findById(commentId);
        Optional<Users> user = usersRepo.findById(userId);

        if(comment.isPresent() && user.isPresent()){

            boolean check = user.get().getLikedComments().add(comment.get());
            if(!check){
                return dislikeComment(commentId , userId);
            }
            usersRepo.save(user.get());

            comment.get().getLikedUsers().add(user.get());
            long newLikes = comment.get().getLikedUsers().size();
            comment.get().setLikes(newLikes);
            commentRepo.save(comment.get());
            return newLikes;
        }

        return 0;
    }

    public long dislikeComment(long commentId , long userId){
        Optional<Comment> comment = commentRepo.findById(commentId);
        Optional<Users> user = usersRepo.findById(userId);

        if(comment.isPresent() && user.isPresent()){

            boolean check = user.get().getLikedComments().add(comment.get());
            if(!check){
                return likeComment(commentId , userId);
            }
            usersRepo.save(user.get());

            comment.get().getLikedUsers().add(user.get());
            long newLikes = comment.get().getLikedUsers().size();
            comment.get().setLikes(newLikes);
            commentRepo.save(comment.get());
            return newLikes;
        }

        return 0;
    }


    public List<Comment> getCommentsByUserId(long userId){
    
        Optional<Users> user = usersRepo.findById(userId);

        if(user.isPresent()){
            return commentRepo.findCommentByUser(userId);
        }
        
        return null;

    }

    public List<Comment> getCommentByPostId(long postId){

        Optional<Post> post = postRepo.findById(postId);

        if(post.isPresent()){
            return commentRepo.findByPostPostId(postId);
        }

        return null;

    }

    public long getLikes(long commentId){
        Optional<Comment> comment = commentRepo.findById(commentId);

        if(comment.isPresent()){
            return comment.get().getLikes();
        }

        return 0;
    }


}
