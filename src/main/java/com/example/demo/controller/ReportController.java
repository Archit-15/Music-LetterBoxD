package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Report;
import com.example.demo.service.ReportService;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    
    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("/unapproved")
    public ResponseEntity<List<Report>> getUnapprovedReports(){
        List<Report> uaReports = reportService.getUnapprovedReports();

        if(uaReports != null){
            return ResponseEntity.status(200).body(uaReports);
        }

        return ResponseEntity.status(404).build();
    }

    @PostMapping("/create/{userId}/{postId}")
    public ResponseEntity<Report> createReport(@RequestBody Report report, @PathVariable long userId, @PathVariable long postId){
        Report createdReport = reportService.createReport(userId, postId, report);

        if(createdReport != null){
            return ResponseEntity.status(201).body(createdReport);
        }

        return ResponseEntity.status(404).build();
    }
    

    @DeleteMapping("/delete/{reportId}")
    public ResponseEntity<Boolean> deleteReport(@PathVariable long reportId){
        boolean deleteVal = reportService.deleteReport(reportId);

        if(deleteVal){
            return ResponseEntity.status(204).body(true);
        }

        return ResponseEntity.status(404).body(false);
    }

    @DeleteMapping("/approve/{reportId}")
    public ResponseEntity<Boolean> approveReport(@PathVariable long reportId){
        boolean reportStatus = reportService.approveReport(reportId);

        if(reportStatus){
            return ResponseEntity.status(201).body(reportStatus);
        }

        return ResponseEntity.status(404).build();
    }


}
