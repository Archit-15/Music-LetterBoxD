package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Post {
    
    @Id
    @GeneratedValue
    private long postId;

    private String postContent;

    private boolean postStatus;

    private long likes;

    @ManyToOne
    @JsonBackReference("user-post")
    private Users user;

    @OneToMany(mappedBy="post")
    @JsonManagedReference("post-report")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    @JsonManagedReference("post-comment")
    private List<Comment> comments = new ArrayList<>();


    @ManyToMany(mappedBy ="likedPosts")
    @JsonIgnore
    private Set<Users> likedUsers = new HashSet<>();

    private LocalDateTime createdAt;

    public Post() {
    }

    public Post(long postId, String postContent, boolean postStatus, long likes, Users user, List<Report> reports,
            List<Comment> comments, Set<Users> likedUsers, LocalDateTime createdAt) {
        this.postId = postId;
        this.postContent = postContent;
        this.postStatus = postStatus;
        this.likes = likes;
        this.user = user;
        this.reports = reports;
        this.comments = comments;
        this.likedUsers = likedUsers;
        this.createdAt = createdAt;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public boolean isPostStatus() {
        return postStatus;
    }

    public void setPostStatus(boolean postStatus) {
        this.postStatus = postStatus;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<Users> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(Set<Users> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Post [postId=" + postId + ", postContent=" + postContent + ", postStatus=" + postStatus + ", likes="
                + likes + ", user=" + user + ", reports=" + reports + ", comments=" + comments + ", likedUsers="
                + likedUsers + ", createdAt=" + createdAt + "]";
    }

   

    
   
}
