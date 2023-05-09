package com.stock.service;

import com.stock.entity.WatchList;
import com.stock.exception.ResourceNotFoundInWatchlist;
import com.stock.model.WatchListModel;
import com.stock.twelvedata.model.HomeScreen;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WatchListService {
    void addToWatchlist(WatchListModel watchListModel);
    void removeFromWatchlist(WatchListModel watchListModel) throws ResourceNotFoundInWatchlist;
    List<HomeScreen> getAllWatchlist(String email);
}
