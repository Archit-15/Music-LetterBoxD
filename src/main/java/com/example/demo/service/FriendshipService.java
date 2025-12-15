package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.FriendsDTO;
import com.example.demo.exception.AlreadyFriendsException;
import com.example.demo.model.Friendship;
import com.example.demo.model.Users;
import com.example.demo.repository.FriendRepo;
import com.example.demo.repository.UsersRepo;

@Service
public class FriendshipService {
    
    private FriendRepo friendshipRepo;
    private UsersRepo usersRepo;
    
    @Autowired 
    public FriendshipService(FriendRepo friendshipRepo, UsersRepo usersRepo){
        this.friendshipRepo = friendshipRepo;
        this.usersRepo = usersRepo;
    }

    public Friendship createFrienship(long userId, long friendId, Friendship friendship){
        Optional<Users> user = usersRepo.findById(userId);
        Optional<Users> friend = usersRepo.findById(friendId);

        if(user.isPresent() && friend.isPresent()){

            int occurence = friendshipRepo.countFriendshipOccurence(userId, friendId);
            if(occurence>=1){
                throw new AlreadyFriendsException(400,"You are already friends with this user.");
            }

            friendship.setCreatedAt(LocalDateTime.now());
            friendship.setUser(user.get());
            friendship.setFriend(friend.get());

            return friendshipRepo.save(friendship);
        }
        return null;
    }

    public boolean deleteFrienship(long friendshipId){

        Optional<Friendship> friendship = friendshipRepo.findById(friendshipId);

        if(friendship.isPresent()){
            friendshipRepo.delete(friendship.get());
            return true;
        } 

        return true;

    }

    public List<FriendsDTO> getAllFriendsByUserId(long userId){

        Optional<Users> user = usersRepo.findById(userId);

        if(user.isPresent()){
            List<FriendsDTO> all = friendshipRepo.allSentFriendsOfUser(userId);
            List<FriendsDTO> recieved = friendshipRepo.allReceivedFriendsOfUser(userId);
            all.addAll(recieved);
            return all;
        }
        return null;

    }

    public List<FriendsDTO> getAllRequestByUserId(long userId){

        Optional<Users> user = usersRepo.findById(userId);

        if(user.isPresent()){
            List<FriendsDTO> requests = friendshipRepo.getAllRequests(userId);
            return requests;
            
        }
        return null;

    }

    public Friendship acceptRequest(long friendshipId){

        Optional<Friendship> friendship = friendshipRepo.findById(friendshipId);

        if(friendship.isPresent()){
            friendship.get().setFriendshipStatus(true);
            return friendshipRepo.save(friendship.get());
        }

        return null;
    }

     public boolean denyRequest(long friendshipId){

        Optional<Friendship> friendship = friendshipRepo.findById(friendshipId);

        if(friendship.isPresent()){
            friendshipRepo.delete(friendship.get());
            return true;
        }

        return false;

    }



}
