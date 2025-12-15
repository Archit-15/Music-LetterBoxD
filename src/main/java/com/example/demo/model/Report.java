package com.example.demo.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Report {

    @Id
    @GeneratedValue
    private long reportId;

    private String reportText;

    private boolean reportStatus;   //TRUE -> ACCEPTED , FALSE -> UNACCEPTED


    @ManyToOne
    @JsonBackReference("post-report")
    private Post post;

    @ManyToOne
    @JsonBackReference("user-report")
    private Users user;

    private LocalDateTime createdAt;


    public Report() {
    }

    public Report(long reportId, String reportText, boolean reportStatus, Post post, Users user , LocalDateTime createdAt) {
        this.reportId = reportId;
        this.reportText = reportText;
        this.reportStatus = reportStatus;
        this.post = post;
        this.user = user;
        this.createdAt = createdAt;
    }

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }

    public boolean isReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(boolean reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Report [reportId=" + reportId + ", reportText=" + reportText + ", reportStatus=" + reportStatus
                + ", post=" + post + ", user=" + user + ",createdAt=" + createdAt + "]";
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }  

}
