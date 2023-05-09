package com.stock.repository;

import com.stock.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade,String> {
    List<Trade> getAllByEmail(String email);
    @Query("select t from Trade t where t.symbol=:symbol and t.forBuySell=:forBuySell and t.quantity=:quantity and t.buyingPrice=:sellingPrice")
    Trade getParticularaTrade(String symbol, String forBuySell,double quantity,double sellingPrice);
    @Query("select t from Trade t where t.forBuySell=:forBuySell and t.symbol=:symbol and t.sellingPrice=:buyingPrice")
    Trade isAnyOneSelling(String forBuySell,String symbol,double buyingPrice);
}
