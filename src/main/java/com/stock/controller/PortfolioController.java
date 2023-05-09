package com.stock.controller;

import com.stock.model.PortfolioModel;
import com.stock.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/myPortfolio")
    ResponseEntity<List<PortfolioModel>> getAllPortfolioStocks(@RequestHeader("email") String email){
        List<PortfolioModel> portfolioModelList = this.portfolioService.getAllStocksFromPortfolio(email);
        return ResponseEntity.ok(portfolioModelList);
    }

    ////store data in my portfolio part is left to code
}
