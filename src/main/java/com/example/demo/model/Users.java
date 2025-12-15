package com.example.demo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Users {
    @Id
    @GeneratedValue
    private long id;

    private String username;

    private String email;

    private String password;

    private String role;        //USER or ADMIN(all caps)


    @OneToMany(mappedBy="user")
    @JsonManagedReference("user-post")
    private List<Post> createdPosts = new ArrayList<>();

    @OneToMany(mappedBy="user")
    @JsonManagedReference("user-report")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy="user")
    @JsonManagedReference("user")
    private List<Friendship> requestsRecieved = new ArrayList<>();

    @OneToMany(mappedBy="friend")
    @JsonManagedReference("friend")
    private List<Friendship> requestsSent = new ArrayList<>();

    @OneToMany(mappedBy="user")
    @JsonManagedReference("user-comment")
    private List<Comment> commentsMade = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "user_post",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="post_postId")
    )
    private Set<Post> likedPosts = new HashSet<>();


    @ManyToMany
    @JoinTable(
        name = "user_comment",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="comment_commentId")
    )
    private Set<Comment> likedComments = new HashSet<>();



    public Users() {
    }

    public Users(long id, String username, String email, String password, String role, List<Post> createdPosts,
            List<Report> reports, List<Friendship> requestsRecieved, List<Friendship> requestsSent,
            List<Comment> commentsMade, Set<Post> likedPosts, Set<Comment> likedComments) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdPosts = createdPosts;
        this.reports = reports;
        this.requestsRecieved = requestsRecieved;
        this.requestsSent = requestsSent;
        this.commentsMade = commentsMade;
        this.likedPosts = likedPosts;
        this.likedComments = likedComments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Post> getCreatedPosts() {
        return createdPosts;
    }

    public void setCreatedPosts(List<Post> createdPosts) {
        this.createdPosts = createdPosts;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public List<Friendship> getRequestsRecieved() {
        return requestsRecieved;
    }

    public void setRequestsRecieved(List<Friendship> requestsRecieved) {
        this.requestsRecieved = requestsRecieved;
    }

    public List<Friendship> getRequestsSent() {
        return requestsSent;
    }

    public void setRequestsSent(List<Friendship> requestsSent) {
        this.requestsSent = requestsSent;
    }

    public List<Comment> getCommentsMade() {
        return commentsMade;
    }

    public void setCommentsMade(List<Comment> commentsMade) {
        this.commentsMade = commentsMade;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(Set<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public Set<Comment> getLikedComments() {
        return likedComments;
    }

    public void setLikedComments(Set<Comment> likedComments) {
        this.likedComments = likedComments;
    }

    @Override
    public String toString() {
        return "Users [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", role="
                + role + ", createdPosts=" + createdPosts + ", reports=" + reports + ", requestsRecieved="
                + requestsRecieved + ", requestsSent=" + requestsSent + ", commentsMade=" + commentsMade
                + ", likedPosts=" + likedPosts + ", likedComments=" + likedComments + "]";
    }
   
       
}
