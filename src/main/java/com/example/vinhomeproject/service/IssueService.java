package com.example.vinhomeproject.service;

import com.example.vinhomeproject.dto.IssueDTO;
import com.example.vinhomeproject.mapper.IssueMapper;
import com.example.vinhomeproject.models.Issue;
import com.example.vinhomeproject.models.Zone;
import com.example.vinhomeproject.repositories.IssueRepository;
import com.example.vinhomeproject.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private IssueMapper issueMapper;

    public ResponseEntity<ResponseObject> getAll(){
        List<Issue> issues = issueRepository.findAll();
        if(!issues.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Get all issues successfully",
                    issues
            ));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "List issues null",
                    null
            ));
        }
    }
    public ResponseEntity<ResponseObject> createIssue(IssueDTO issueDTO){
        Issue issue = issueMapper.toIssue(issueDTO);
        issue.setStatus(true);
        issueRepository.save(issue);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Create  issues successfully",
                null
        ));
    }

    public ResponseEntity<ResponseObject> update(Long id,IssueDTO issueDTO){
        Optional<Issue> issue = issueRepository.findIssueById(id);
        if(issue.isPresent()){
            issueMapper.update(issueDTO,issue.get());
            issueRepository.save(issue.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Update  issues successfully",
                    null
            ));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Not found issues ",
                null
        ));
    }

    public ResponseEntity<ResponseObject> delete(Long id){
        Optional<Issue> issue = issueRepository.findIssueById(id);
        if(issue.isPresent()){
            issue.get().setStatus(!issue.get().isStatus());
            issueRepository.save(issue.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Update  issues successfully",
                    null
            ));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Not found issues ",
                null
        ));
    }
    public ResponseEntity<ResponseObject> getById(Long id){
        Optional<Issue> issues = issueRepository.findIssueById(id);
        if(issues.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Get issues by id successfully",
                    issues
            ));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Not found issues by id",
                    null
            ));
        }
    }
}
