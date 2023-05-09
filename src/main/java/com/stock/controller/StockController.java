package com.stock.controller;

import com.stock.twelvedata.model.*;
import com.stock.twelvedata.service.TwelveDataApiClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StockController {

    private TwelveDataApiClient twelveDataApiClient;

    public StockController(TwelveDataApiClient twelveDataApiClient) {
        this.twelveDataApiClient = twelveDataApiClient;
    }

    @GetMapping("/quotedata/{symbol}")
    public ResponseEntity<StockData> quoteData(@PathVariable String symbol) throws IOException {
        StockData quoteData = this.twelveDataApiClient.getQuoteData(symbol);
        return ResponseEntity.ok(quoteData);
    }


    @GetMapping("/liveStockPrice/{symbol}")
    public ResponseEntity<LiveStockPrice> liveStockPrice(@PathVariable String symbol) throws IOException {
        LiveStockPrice liveStockPrice = this.twelveDataApiClient.getLiveStockPrice(symbol);
        return ResponseEntity.ok(liveStockPrice);
    }


    @GetMapping("/allQuotes")
    public ResponseEntity<List<StockData>> allQuoteData() throws IOException {
        List<StockData> quoteData = this.twelveDataApiClient.getAllQuoteData();
        return ResponseEntity.ok(quoteData);
    }


    @GetMapping("/allTimeSeriesStock")
    public ResponseEntity<AllTimeSeriesStock> allTimeSeriesStock() throws IOException {
        AllTimeSeriesStock allTimeSeriesStock = this.twelveDataApiClient.getAllTimeSeriesStock();
        return ResponseEntity.ok(allTimeSeriesStock);
    }


    @GetMapping("/meta-data/{symbol}")
    public ResponseEntity<FarragoOfData> farragoOfData(@PathVariable String symbol) throws IOException {
        FarragoOfData farragoOfData = this.twelveDataApiClient.getFarragoOfData(symbol);
        System.out.println(farragoOfData);
        return ResponseEntity.ok(farragoOfData);
    }

    @GetMapping("/")
    public ResponseEntity<List<HomeScreen>> getHomeScreenData() throws IOException{
        List<HomeScreen> homeScreenData = this.twelveDataApiClient.getHomeScreenData();
        System.out.println(homeScreenData);
        return ResponseEntity.ok(homeScreenData);
    }

}
