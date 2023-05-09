package com.stock.controller;

import com.stock.entity.Portfolio;
import com.stock.entity.Trade;
import com.stock.exception.StockForSaleNotFound;
import com.stock.model.TradeModel;
import com.stock.repository.HistoryRepository;
import com.stock.repository.PortfolioRepository;
import com.stock.repository.TradeRepository;
import com.stock.service.AddMoneyService;
import com.stock.service.PortfolioService;
import com.stock.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TradeController {
    @Autowired
    private TradeService tradeService;
    @Autowired
    private AddMoneyService addMoneyService;
    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private PortfolioService portfolioService;



    @PostMapping("/transaction")
    ResponseEntity<?> buySellTrade(@RequestHeader("email") String email, @RequestBody TradeModel tradeModel) throws StockForSaleNotFound {
        tradeModel.setEmail(email);

        if (tradeModel.getForBuySell().equals("Buy")) {
            double totalCost = tradeModel.getBuyingPrice() * tradeModel.getQuantity();
            double currentBalance = addMoneyService.getCurrentBalance(email);
            if (totalCost <= currentBalance) {
                Trade trade = this.tradeRepository.isAnyOneSelling("Sell", tradeModel.getSymbol(), tradeModel.getBuyingPrice());
                if (trade != null && trade.getQuantity() == tradeModel.getQuantity()) {
                    this.portfolioService.tradeToPortfolio(tradeModel);
                    this.addMoneyService.setSubstractBalance(totalCost, tradeModel.getEmail());
                    //----------------------------------------------------------------------------------------------------------------------------------
                    this.addMoneyService.setAddBalance(totalCost, trade.getEmail());
                    this.tradeService.saveTradeToHistory(trade);
                    this.tradeRepository.delete(trade);
                    return ResponseEntity.ok("Stock has been added to Portfolio");
                } else {
                    this.tradeService.storeTrade(tradeModel);
                    return ResponseEntity.ok("Stock has been dropped for purchase successfully");
                }
            } else {
                return ResponseEntity.badRequest().body("Insufficient Balance");
            }
        }

        if(tradeModel.getForBuySell().equals("Sell")){
            Portfolio portfolio = this.portfolioRepository.getParticularPortfolio(tradeModel.getEmail(),tradeModel.getSymbol(),tradeModel.getQuantity());
            Trade trade = this.tradeRepository.getParticularaTrade(tradeModel.getSymbol(),"Buy",tradeModel.getQuantity(),tradeModel.getSellingPrice());
            if (trade == null) {
                this.tradeService.storeTrade(tradeModel);
                return ResponseEntity.ok("Stock has been dropped for sell successfully");
            } else if (trade.getQuantity() == tradeModel.getQuantity() && trade.getBuyingPrice() == tradeModel.getSellingPrice()) {
                this.tradeService.saveTradeModelToHistory(tradeModel);
                this.addMoneyService.setAddBalance(tradeModel.getSellingPrice() * tradeModel.getQuantity(), tradeModel.getEmail());
                //------------------------------------------------------------------------------------------------------------------------------------------
                this.addMoneyService.setSubstractBalance(tradeModel.getSellingPrice() * tradeModel.getQuantity(), trade.getEmail());
                this.tradeService.saveTradeToPortfolio(trade);
                this.tradeRepository.delete(trade);
                return ResponseEntity.ok("Stock has been sold successfully");
            } else {
                throw new StockForSaleNotFound("Stock that user is trying to sell, but they do not possess.");
            }

        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/trade")
    ResponseEntity<List<TradeModel>> getAllTrade(@RequestHeader("email") String email){
        List<TradeModel> tradeModelList = this.tradeService.getAllTrade(email);
        return ResponseEntity.ok(tradeModelList);
    }
}
