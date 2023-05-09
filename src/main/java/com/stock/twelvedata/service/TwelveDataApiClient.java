package com.stock.twelvedata.service;

import com.stock.twelvedata.model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface TwelveDataApiClient {
    StockData getQuoteData(String symbol) throws IOException;
    List<StockData> getAllQuoteData()throws IOException;
    LiveStockPrice getLiveStockPrice(String symbol)throws IOException;
    AllTimeSeriesStock getAllTimeSeriesStock() throws IOException;
    FarragoOfData getFarragoOfData(String symbol) throws IOException;
    List<HomeScreen> getHomeScreenData() throws IOException;
}
