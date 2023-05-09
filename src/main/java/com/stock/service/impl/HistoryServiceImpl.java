package com.stock.service.impl;

import com.stock.entity.History;
import com.stock.entity.Trade;
import com.stock.model.HistoryModel;
import com.stock.model.TradeModel;
import com.stock.repository.HistoryRepository;
import com.stock.service.HistoryService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;
    @Override
    public List<HistoryModel> getEntireHistory(String email) {
        List<History> historyList = this.historyRepository.getAllByEmail(email);

        List<HistoryModel> historyModelList = new CopyOnWriteArrayList<>();

        HistoryModel historyModel = new HistoryModel();

        for (History history : historyList){
            historyModel.setEmail(history.getEmail());
            historyModel.setSymbol(history.getSymbol());
            historyModel.setBuyingPrice(history.getBuyingPrice());
            historyModel.setSellingPrice(history.getSellingPrice());
            historyModel.setQuantity(history.getQuantity());
            historyModel.setProfitLoss(whetherProfitOrLoss(history));
            historyModel.setVariation(giveVariation(history));

            historyModelList.add(historyModel);
        }
        return historyModelList;
    }

    //using as a function parameter
    public String whetherProfitOrLoss(History history){
        if(history.getBuyingPrice()>history.getSellingPrice()){
            return "Loss";
        }else if(history.getBuyingPrice()<history.getSellingPrice()){
            return "Profit";
        }else{
            return "Equal";
        }
    }

    //using as function parameter
    public double giveVariation(History history){
        if(history.getBuyingPrice()*history.getQuantity()>history.getSellingPrice()*history.getQuantity()){
            return (history.getBuyingPrice()*history.getQuantity())-(history.getSellingPrice()*history.getQuantity());
        }else if(history.getBuyingPrice()*history.getQuantity()<history.getSellingPrice()*history.getQuantity()){
            return Double.parseDouble("+"+(history.getSellingPrice()*history.getQuantity()-history.getBuyingPrice()*history.getQuantity()));
        }else{
            return 0;
        }
    }
}
