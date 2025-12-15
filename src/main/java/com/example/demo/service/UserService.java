package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.Users;
import com.example.demo.repository.CommentRepo;
import com.example.demo.repository.FriendRepo;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.ReportRepo;
import com.example.demo.repository.UsersRepo;

@Service
public class UserService {
    
    private UsersRepo usersRepo;
    private PostRepo postRepo;
    private ReportRepo reportRepo;
    private CommentRepo commentRepo;
    private FriendRepo friendRepo;

    @Autowired
    public UserService(UsersRepo usersRepo, PostRepo postRepo, ReportRepo reportRepo, CommentRepo commentRepo, FriendRepo friendRepo){
        this.usersRepo = usersRepo;
        this.postRepo = postRepo;
        this.reportRepo = reportRepo;
        this.commentRepo = commentRepo;
        this.friendRepo = friendRepo;
    }

    public Users createUser(Users user){

        Users check = usersRepo.findByEmail(user.getEmail());

        if(check != null){
            return null;
        }

        return usersRepo.save(user);
    }

    public boolean deleteUser(long userId){

        Optional<Users> u  = usersRepo.findById(userId);
        if(u.isPresent()){
            commentRepo.deleteByUserId(userId);
            friendRepo.deleteByUserId(userId);
            reportRepo.deleteByUserId(userId);
            postRepo.deleteByUserId(userId);
            
            for(Comment comment : u.get().getLikedComments()){
                comment.getLikedUsers().remove(u.get());
            }
            u.get().getLikedComments().clear();

            for(Post post : u.get().getLikedPosts()){
                post.getLikedUsers().remove(u.get());
            }
            u.get().getLikedPosts().clear();

            usersRepo.delete(u.get());
            return true;
        }
        return false;

    }

    public List<Users> getAllUsers(){
        return usersRepo.findAll();
    }

    public Users getUserById(long userId){
        Optional<Users> u = usersRepo.findById(userId);
        
        if (u.isPresent()) {
            return u.get();
        }

        return null;
    }

    public Users updateUser(long userId , Users newUser){

        Optional<Users> user = usersRepo.findById(userId);

        if(user.isPresent()){

            user.get().setEmail(newUser.getEmail());
            user.get().setPassword(newUser.getPassword());
            user.get().setUsername(newUser.getUsername());
            return usersRepo.save(user.get());
        }

        return null;

    }


}
