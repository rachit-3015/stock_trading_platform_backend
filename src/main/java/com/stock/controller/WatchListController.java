package com.stock.controller;

import com.stock.exception.ResourceNotFoundInWatchlist;
import com.stock.model.WatchListModel;
import com.stock.service.WatchListService;
import com.stock.twelvedata.model.HomeScreen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class WatchListController {
    @Autowired
    private WatchListService watchListService;

    @PostMapping("/add")
    ResponseEntity<?> add(@RequestHeader("email") String email,@RequestBody WatchListModel watchListModel){
        watchListModel.setEmail(email);
        this.watchListService.addToWatchlist(watchListModel);
        return ResponseEntity.ok("Stock has been added to WatchList");
    }

    @DeleteMapping("/remove")
    ResponseEntity<?> remove(@RequestHeader("email") String email,@RequestBody WatchListModel watchListModel) throws ResourceNotFoundInWatchlist {
        watchListModel.setEmail(email);
        this.watchListService.removeFromWatchlist(watchListModel);
        return ResponseEntity.ok("Stock has been removed from Watchlist");
    }

    @GetMapping("myWatchlist")
    ResponseEntity<List<HomeScreen>> getAllStocksOfWatchlist(@RequestHeader("email") String email){
        List<HomeScreen> homeScreenList = this.watchListService.getAllWatchlist(email);
        return ResponseEntity.ok(homeScreenList);
    }
}
