package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Report;

import jakarta.transaction.Transactional;

@Repository
public interface ReportRepo extends JpaRepository<Report, Long>{

    @Modifying
    @Transactional
    @Query("Delete from Report r where r.post.postId = ?1")
    int deleteByPostId(long postId);

    @Modifying
    @Transactional
    @Query("Delete from Report r where r.user.id = ?1")
    int deleteByUserId(long userId);

    @Query("Select r from Report r where r.reportStatus = false")
    List<Report> findUnapprovedReports();

}
