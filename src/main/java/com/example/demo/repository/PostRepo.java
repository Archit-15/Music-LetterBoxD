package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Post;

import jakarta.transaction.Transactional;

@Repository
public interface PostRepo extends JpaRepository<Post, Long>{
    
    @Query("Select p from Post p where p.postStatus = false")
    List<Post> getAllUnapprovedPosts();

    @Query("Select p from Post p where p.postStatus = true")
    List<Post> getAllApprovedPosts();


    @Query("Select p from Post p where p.user.id=?1 AND p.postStatus=true")
    List<Post> getApprovedPostsByUserId(long userId);

    @Query("Select p from Post p where p.user.id=?1 AND p.postStatus=false")
    List<Post> getDisapprovedPostsByUserId(long userId);

    @Modifying
    @Transactional
    @Query("Delete from Post p  where p.user.id = ?1")
    int deleteByUserId(long userId);
}
