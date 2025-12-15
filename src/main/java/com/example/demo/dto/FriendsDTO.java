package com.example.demo.dto;

public class FriendsDTO {
    
    private long friendshipId;
    private String username;
    private String email;

    public FriendsDTO() {
    }

    public FriendsDTO(String username, String email,long friendshipId) {
        this.username = username;
        this.email = email;
        this.friendshipId = friendshipId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "FriendsDTO [username=" + username + ", email=" + email + "]";
    }

    public long getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(long friendshipId) {
        this.friendshipId = friendshipId;
    }
    

}
