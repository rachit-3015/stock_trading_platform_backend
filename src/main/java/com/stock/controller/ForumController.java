package com.stock.controller;

import com.stock.entity.Forum;
import com.stock.model.ForumModel;
import com.stock.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ForumController {
    @Autowired
    private ForumService forumService;

    @PostMapping("/comment")
    public ResponseEntity<?> saveThisMessage(@RequestHeader("email") String email, ForumModel forumModel){
        this.forumService.publishTheMessage(email,forumModel);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/forum")
    public ResponseEntity<List<Forum>> getAllMessages(){
        List<Forum> forumList = this.forumService.getAllPublishedMessages();
        return ResponseEntity.ok(forumList);
    }
}
