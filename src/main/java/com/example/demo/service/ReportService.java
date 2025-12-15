package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Post;
import com.example.demo.model.Report;
import com.example.demo.model.Users;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.ReportRepo;
import com.example.demo.repository.UsersRepo;

@Service
public class ReportService {
    
    private ReportRepo reportRepo;
    private UsersRepo usersRepo;
    private PostRepo postRepo;
    private PostService postService;

    @Autowired
    public ReportService(ReportRepo reportRepo, UsersRepo usersRepo, PostRepo postRepo, PostService postService){
        this.reportRepo = reportRepo;
        this.usersRepo = usersRepo;
        this.postRepo = postRepo;
        this.postService = postService;
    }

    public List<Report> getUnapprovedReports(){
        return reportRepo.findUnapprovedReports();
    }

    public Report createReport(long userId, long postId, Report report){
        Optional<Users> user = usersRepo.findById(userId);
        Optional<Post> post = postRepo.findById(postId);

        if(user.isPresent() && post.isPresent()){

            if (!user.get().getReports().contains(report)) {
                report.setUser(user.get());
                report.setPost(post.get());
                report.setCreatedAt(LocalDateTime.now());
                Report savedReport = reportRepo.save(report);

                user.get().getReports().add(savedReport);
                post.get().getReports().add(savedReport);

                usersRepo.save(user.get());
                postRepo.save(post.get());
                return savedReport;
            }

        }

        return null;

    }

    public boolean deleteReport(long reportId){

        Optional<Report> report = reportRepo.findById(reportId);

        if(report.isPresent()){
            reportRepo.delete(report.get());
            return true;
        }

        return false;

    }

    public boolean approveReport(long reportId){

        Optional<Report> report = reportRepo.findById(reportId);

        if(report.isPresent()){
            long postId = report.get().getPost().getPostId();
            reportRepo.delete(report.get());
            return postService.deletePost(postId);
        }

        return false;

    }   

}
