package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {
    
    @Id
    @GeneratedValue
    private long commentId;

    private String commentText;

    private long likes;

    @ManyToOne
    @JsonBackReference("post-comment")
    // @JsonIgnore
    private Post post;

    @ManyToOne
    @JsonBackReference("user-comment")
    // @JsonIgnore
    private Users user;
    

    @ManyToMany(mappedBy ="likedComments")
    @JsonIgnore
    private Set<Users> likedUsers = new HashSet<>();

    public Comment() {
    }

    public Comment(long commentId, String commentText, long likes, Post post, Set<Users> likedUsers , Users user) {
        this.commentId = commentId;
        this.commentText = commentText;
        this.likes = likes;
        this.post = post;
        this.likedUsers = likedUsers;
        this.user = user;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Set<Users> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(Set<Users> likedUsers) {
        this.likedUsers = likedUsers;
    }

    @Override
    public String toString() {
        return "Comment [commentId=" + commentId + ", commentText=" + commentText + ", likes=" + likes + ", post="
                + post + ", user=" + user + ", likedUsers=" + likedUsers + "]";
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

}
