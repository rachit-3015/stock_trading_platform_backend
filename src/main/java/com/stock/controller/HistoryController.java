package com.stock.controller;

import com.stock.model.HistoryModel;
import com.stock.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class HistoryController {
    @Autowired
    private HistoryService historyService;
    @GetMapping("/history")
    public ResponseEntity<List<HistoryModel>> getAllHistory(@RequestHeader("email") String email){
        List<HistoryModel> historyModelList = this.historyService.getEntireHistory(email);
        return ResponseEntity.ok(historyModelList);
    }
}
