package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.FriendsDTO;
import com.example.demo.model.Friendship;

import jakarta.transaction.Transactional;

@Repository
public interface FriendRepo extends JpaRepository<Friendship, Long>{
    

    @Query("Select COUNT(friendshipId) from Friendship f where (f.user.id=?1 AND f.friend.id=?2) OR (f.user.id=?2 AND f.friend.id=?1)")
    int countFriendshipOccurence(long userId , long friendId);

    @Query("Select new com.example.demo.dto.FriendsDTO(f.friend.username , f.friend.email, f.friendshipId) from Friendship f where f.user.id = ?1 AND f.friendshipStatus = true")
    List<FriendsDTO> allReceivedFriendsOfUser(long userId);

    @Query("Select new com.example.demo.dto.FriendsDTO(f.user.username , f.user.email, f.friendshipId) from Friendship f where f.friend.id =?1 AND f.friendshipStatus = true")
    List<FriendsDTO> allSentFriendsOfUser(long userId);

    @Query("Select new com.example.demo.dto.FriendsDTO(f.friend.username , f.friend.email, f.friendshipId) from Friendship f where f.user.id = ?1 AND f.friendshipStatus = false")
    List<FriendsDTO> getAllRequests(long userId);

    @Modifying
    @Transactional
    @Query("Delete from Friendship f where f.user.id = ?1 OR f.friend.id = ?1")
    int deleteByUserId(long userId);

}
