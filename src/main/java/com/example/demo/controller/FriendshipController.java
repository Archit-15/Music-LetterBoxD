package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.FriendsDTO;
import com.example.demo.model.Friendship;
import com.example.demo.service.FriendshipService;

@RestController
@RequestMapping("/api/friendship")
public class FriendshipController {
    
    private FriendshipService friendshipService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService){
        this.friendshipService = friendshipService;
    }

    @PostMapping("send/{userId}/{friendId}")
    public ResponseEntity<Friendship> createFriendship(@RequestBody Friendship friendship, @PathVariable long userId, @PathVariable long friendId){
        
        Friendship nf = friendshipService.createFrienship(userId, friendId, friendship);

        if(nf != null){
            return ResponseEntity.status(201).body(nf);
        }

        return ResponseEntity.status(404).build();

    }

    @PostMapping("accept/{friendshipId}")
    public ResponseEntity<Friendship> acceptFriendship(@PathVariable long friendshipId){
        
        Friendship newf = friendshipService.acceptRequest(friendshipId);

        if(newf != null){
            return ResponseEntity.status(201).body(newf);
        }

        return ResponseEntity.status(404).build();

    }

    @DeleteMapping("deny/{friendshipId}")
    public ResponseEntity<Boolean> denyFriendship(@PathVariable long friendshipId){
        
        boolean denyVal = friendshipService.denyRequest(friendshipId);

        if(denyVal){
            return ResponseEntity.status(204).body(true);
        }

        return ResponseEntity.status(404).body(false);

    }


    @GetMapping("/request/{userId}")
     public ResponseEntity<List<FriendsDTO>> getAllRequestsByUserId(@PathVariable long userId){
        
        List<FriendsDTO> requests = friendshipService.getAllRequestByUserId(userId);

        if(!requests.isEmpty()){
            return ResponseEntity.status(200).body(requests);
        }

        return ResponseEntity.status(404).build();

    }


    @GetMapping("/{userId}")
     public ResponseEntity<List<FriendsDTO>> getAllFriendsByUserId(@PathVariable long userId){
        
        List<FriendsDTO> friends = friendshipService.getAllFriendsByUserId(userId);

        if(!friends.isEmpty()){
            return ResponseEntity.status(200).body(friends);
        }

        return ResponseEntity.status(404).build();

    }

    @DeleteMapping("/delete/{friendshipId}")
    public ResponseEntity<Boolean> deleteFriendship(@PathVariable long friendshipId){
        
        boolean denyVal = friendshipService.deleteFrienship(friendshipId);

        if(denyVal){
            return ResponseEntity.status(204).body(true);
        }

        return ResponseEntity.status(404).body(false);

    }



}
