package com.example.demo.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Friendship {
    
    @Id
    @GeneratedValue
    private long friendshipId;

    @ManyToOne
    @JsonBackReference("user")
    private Users user;

    @ManyToOne
    @JsonBackReference("friend")
    private Users friend;

    private LocalDateTime createdAt;

    private boolean friendshipStatus;

    public Friendship() {
    }

    public Friendship(long friendshipId, Users user, Users friend, LocalDateTime createdAt , boolean friendshipStatus) {
        this.friendshipId = friendshipId;
        this.user = user;
        this.friend = friend;
        this.createdAt = createdAt;
        this.friendshipStatus = friendshipStatus;
    }

    public long getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(long friendshipId) {
        this.friendshipId = friendshipId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Users getFriend() {
        return friend;
    }

    public void setFriend(Users friend) {
        this.friend = friend;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Friendhsip [friendshipId=" + friendshipId + ", user=" + user + ", friend=" + friend + ", createdAt="
                + createdAt + ", friendshipStatus" + friendshipStatus + "]";
    }

    public boolean isFriendshipStatus() {
        return friendshipStatus;
    }

    public void setFriendshipStatus(boolean friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }

    


}

