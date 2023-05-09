package com.stock.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.entity.History;
import com.stock.entity.Portfolio;
import com.stock.entity.Trade;
import com.stock.model.TradeModel;
import com.stock.repository.HistoryRepository;
import com.stock.repository.PortfolioRepository;
import com.stock.repository.TradeRepository;
import com.stock.service.TradeService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class TradeServiceImpl implements TradeService {
    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String BASE_URL = "https://api.twelvedata.com/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public TradeServiceImpl(){
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    @Override
    public void storeTrade(TradeModel tradeModel) {
        Trade trade = modelMapper.map(tradeModel, Trade.class);
        this.tradeRepository.save(trade);
    }

    @Override
    public List<TradeModel> getAllTrade(String email) {
        List<Trade> tradeList = this.tradeRepository.getAllByEmail(email);

        Type listType = new TypeToken<List<TradeModel>>(){}.getType();
        List<TradeModel> tradeModelList = modelMapper.map(tradeList,listType);

        for(TradeModel tradeModel : tradeModelList){
            String url = String.format("%sprice?symbol=%s&interval=1min&apikey=%s", BASE_URL, tradeModel.getSymbol(), "34296748ee99443ba02419cc39aff308");

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String json = response.body().string();
                JsonNode rootNode = objectMapper.readTree(json);

                tradeModel.setPrice(rootNode.path("price").asDouble());

            }catch (Exception e) {
                e.printStackTrace();
            }

        }
        return tradeModelList;

    }

    @Override
    public void saveTradeModelToHistory(TradeModel tradeModel) {
        History history = modelMapper.map(tradeModel,History.class);
        this.historyRepository.save(history);
    }

    @Override
    public void saveTradeToHistory(Trade trade) {
        History history = modelMapper.map(trade,History.class);
        this.historyRepository.save(history);
    }

    @Override
    public void savePortfolioToTrade(Portfolio portfolio) {
        Trade trade = modelMapper.map(portfolio,Trade.class);
        this.tradeRepository.save(trade);
    }

    @Override
    public void saveTradeToPortfolio(Trade trade) {
        Portfolio portfolio = modelMapper.map(trade,Portfolio.class);
        this.portfolioRepository.save(portfolio);
    }
}
