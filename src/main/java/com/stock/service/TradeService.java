package com.stock.service;

import com.stock.entity.Portfolio;
import com.stock.entity.Trade;
import com.stock.model.TradeModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TradeService {
    void storeTrade(TradeModel tradeModel);
    List<TradeModel> getAllTrade(String email);

    void saveTradeModelToHistory(TradeModel tradeModel);
    void saveTradeToHistory(Trade trade);
    void savePortfolioToTrade(Portfolio portfolio);
    void saveTradeToPortfolio(Trade trade);

}
