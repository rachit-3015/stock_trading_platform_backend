package com.stock.service;

import com.stock.entity.Portfolio;
import com.stock.model.PortfolioModel;
import com.stock.model.TradeModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PortfolioService {
    List<PortfolioModel> getAllStocksFromPortfolio(String email);

    void tradeToPortfolio(TradeModel tradeModel);
    void portfolioToTrade(Portfolio portfolio,String email);
}
