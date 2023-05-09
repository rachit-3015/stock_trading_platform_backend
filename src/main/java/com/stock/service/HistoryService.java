package com.stock.service;

import com.stock.entity.History;
import com.stock.entity.Trade;
import com.stock.model.HistoryModel;
import com.stock.model.TradeModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HistoryService {
    List<HistoryModel> getEntireHistory(String email);
}
