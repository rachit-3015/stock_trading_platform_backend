package com.stock.controller;

import com.stock.model.AddMoneyModel;
import com.stock.service.AddMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AddMoneyController {
    @Autowired
    private AddMoneyService addMoneyService;

    @GetMapping("/addMoney")
    public ResponseEntity<Double> getCurrentBalance(@RequestHeader("email") String email) {
       double value = this.addMoneyService.getCurrentBalance(email);
       return ResponseEntity.ok(value);
    }

    @PostMapping("/addMoney")
    public ResponseEntity<?> addMoney(@RequestHeader("email") String email, @RequestBody AddMoneyModel addMoneyModel){
        addMoneyModel.setEmail(email);
        System.out.println("this is the value "+addMoneyModel.getBalance());
        addMoneyService.addMoney(addMoneyModel);
        return ResponseEntity.ok("Money has been added");
    }

}
