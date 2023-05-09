package com.stock.repository;

import com.stock.entity.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList,String> {
    WatchList getWatchListBySymbol(String symbol);
    void deleteWatchListBySymbol(String symbol);
    List<WatchList> getAllByEmail(String email);

}
