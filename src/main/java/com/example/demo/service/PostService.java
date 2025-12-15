package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.PostLikeDislikeException;
import com.example.demo.model.Post;
import com.example.demo.model.Users;
import com.example.demo.repository.CommentRepo;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.ReportRepo;
import com.example.demo.repository.UsersRepo;


@Service
public class PostService {
    private PostRepo postRepo;
    private UsersRepo usersRepo;
    private CommentRepo commentRepo;
    private ReportRepo reportRepo;

    @Autowired
    public PostService(PostRepo postRepo , UsersRepo usersRepo, CommentRepo commentRepo, ReportRepo reportRepo){
        this.postRepo = postRepo;
        this.usersRepo = usersRepo;
        this.reportRepo = reportRepo;
        this.commentRepo = commentRepo;
    }

    public List<Post> getAllUnapprovedPosts(){
        List<Post> posts = postRepo.getAllUnapprovedPosts();
        if(posts.isEmpty()){
            return  null;
        }
        return posts;
    }

    public List<Post> getAllApprovedPosts(){
        List<Post> posts = postRepo.getAllApprovedPosts();
        if(posts.isEmpty()){
            return null;
        }
        return posts;
    }

    public List<Post> getApprovedPostsByUserId(long userId){
        Optional<Users> user = usersRepo.findById(userId);
        if (user.isPresent()) {
            return postRepo.getApprovedPostsByUserId(userId);
        }
        return null;
    }

    public List<Post> getDisapprovedPostsByUserId(long userId){
        Optional<Users> user = usersRepo.findById(userId);
        if (user.isPresent()) {
            return postRepo.getDisapprovedPostsByUserId(userId);
        }
        return null;
    }

    public Post createPost(long userId , Post post){ 
        Optional<Users> user = usersRepo.findById(userId);

        if(user.isPresent()){
            post.setUser(user.get());
            post.setCreatedAt(LocalDateTime.now());
            user.get().getCreatedPosts().add(post);

            usersRepo.save(user.get());
            return postRepo.save(post);
        }
        
        return null;
    }


    public boolean deletePost(long postId){
      
        Optional<Post> post = postRepo.findById(postId);


        if (post.isPresent()) {
            // post.get().getUser().getCreatedPosts().remove(post.get()); maybe not necessary and happens on own
            for(Users user : post.get().getLikedUsers()){
                user.getLikedPosts().remove(post.get());
            }
            post.get().getLikedUsers().clear();
            
            commentRepo.deleteByPostId(postId);
           
            reportRepo.deleteByPostId(postId);
            
            postRepo.delete(post.get());
            return true;
        }

        return false;

    }

    public Post updatePost(long postId ,Post newPost){
        Optional<Post> post = postRepo.findById(postId);

        if(post.isPresent()){
            post.get().setPostContent(newPost.getPostContent());
            return postRepo.save(post.get());
        }

        return null;

    }

    public long likePost(long postId , long userId){
        Optional<Post> post = postRepo.findById(postId);
        Optional<Users> user = usersRepo.findById(userId);

        if(post.isPresent() && user.isPresent()){

            boolean check = user.get().getLikedPosts().add(post.get());
            //Instead of throwing this here I can just unlike it too
            if(!check){
                throw new PostLikeDislikeException(400 ,"You have already Liked the post.");
            }
            usersRepo.save(user.get());

            post.get().getLikedUsers().add(user.get());
            long newLikes = post.get().getLikedUsers().size();
            post.get().setLikes(newLikes);
            postRepo.save(post.get());
            return newLikes;
        }

        return -1;
    }

    public long unlikePost(long postId , long userId){
       
        Optional<Post> post = postRepo.findById(postId);
        Optional<Users> user = usersRepo.findById(userId);

        if(post.isPresent() && user.isPresent()){

            boolean check = user.get().getLikedPosts().remove(post.get());
            //Instead of throwing this here I can just like it too
            if(!check){
                throw new PostLikeDislikeException(400 ,"You can't Dislike posts you haven't liked.");
            }
            usersRepo.save(user.get());

            post.get().getLikedUsers().remove(user.get());
            long newLikes = post.get().getLikedUsers().size();
            post.get().setLikes(newLikes);
            postRepo.save(post.get());
            return newLikes;
        }

        return -1;

    }

    public long getLikes(long postId){
        Optional<Post> post = postRepo.findById(postId);

        if(post.isPresent()){
            return post.get().getLikes();
        }

        return -1;
    }

    public Post approvePost(long postId){

        Optional<Post> post = postRepo.findById(postId);

        if(post.isPresent()){
            post.get().setPostStatus(true);
            return postRepo.save(post.get());
        }

        return null;

    }

    public boolean disapprovePost(long postId){

        Optional<Post> post = postRepo.findById(postId);

        if(post.isPresent()){
            boolean val = deletePost(postId);
            return val;
        }

        return  false;

    }


}
