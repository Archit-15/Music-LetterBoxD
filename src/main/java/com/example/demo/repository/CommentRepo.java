package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Comment;

import jakarta.transaction.Transactional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long>{

    @Query("Select c from Comment c where c.user.id = ?1")
    List<Comment> findCommentByUser(long userId);
    
    List<Comment> findByPostPostId(long postId);

    @Modifying
    @Transactional
    @Query("Delete from Comment c  where c.user.id = ?1")
    int deleteByUserId(long userId);


    @Modifying
    @Transactional
    @Query("Delete from Comment c where c.post.postId = ?1")
    int deleteByPostId(long postId);

}
