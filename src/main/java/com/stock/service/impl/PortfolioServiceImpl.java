package com.stock.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.entity.Portfolio;
import com.stock.entity.Trade;
import com.stock.model.PortfolioModel;
import com.stock.model.TradeModel;
import com.stock.repository.PortfolioRepository;
import com.stock.repository.TradeRepository;
import com.stock.service.PortfolioService;
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
public class PortfolioServiceImpl implements PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TradeRepository tradeRepository;
    private static final String BASE_URL = "";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public PortfolioServiceImpl(){
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    @Override
    public List<PortfolioModel> getAllStocksFromPortfolio(String email) {
        List<Portfolio> portfolioList = this.portfolioRepository.getAllByEmail(email);
        Type listType = new TypeToken<List<PortfolioModel>>(){}.getType();
        List<PortfolioModel> portfolioModelList = modelMapper.map(portfolioList,listType);

        for(PortfolioModel portfolioModel : portfolioModelList){
            String url = String.format("%sprice?symbol=%s&interval=1min&apikey=%s", BASE_URL, portfolioModel.getSymbol(), "");

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String json = response.body().string();
                JsonNode rootNode = objectMapper.readTree(json);

                //setting live price
                portfolioModel.setPrice(rootNode.path("price").asDouble());

                portfolioModel.setLatestPrice(portfolioModel.getBuyingPrice()*portfolioModel.getQuantity());

                //setting value whether one is in profit or loss
                if(portfolioModel.getPrice()>portfolioModel.getBuyingPrice()){
                    portfolioModel.setProfitLoss("Loss");
                }else if(portfolioModel.getPrice()<portfolioModel.getBuyingPrice()){
                    portfolioModel.setProfitLoss("Profit");
                }else{
                    portfolioModel.setProfitLoss("Stable");
                }

                //setting variation
                double variation = (portfolioModel.getPrice()*portfolioModel.getQuantity())-(portfolioModel.getLatestPrice());
                if(variation>00.00){
                    portfolioModel.setVariation(variation);
                }else if(variation<00.00){
                    portfolioModel.setVariation(Double.parseDouble("-"+variation));
                }else{
                    portfolioModel.setVariation(00.00);
                }


            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return portfolioModelList;
    }

    @Override
    public void tradeToPortfolio(TradeModel tradeModel) {
        Portfolio portfolio = new Portfolio();
        portfolio.setSymbol(tradeModel.getSymbol());
        portfolio.setBuyingPrice(tradeModel.getBuyingPrice());
        portfolio.setQuantity(tradeModel.getQuantity());
        portfolio.setLatestPrice(tradeModel.getQuantity()*tradeModel.getBuyingPrice());
        portfolio.setProfitLoss(evaluateProfileLoss(tradeModel));
        portfolio.setVariation(findVariation(tradeModel));
        this.portfolioRepository.save(portfolio);
    }

    @Override
    public void portfolioToTrade(Portfolio portfolio, String email) {
        Trade trade = new Trade();

    }


    //used as a parameter
    public String evaluateProfileLoss(TradeModel tradeModel) {
        double value = tradeModel.getPrice()-tradeModel.getBuyingPrice();
        if(value>0){
            return "Profit";
        }else if(value<0){
            return "Loss";
        }else{
            return "Equal";
        }
    }

    //used as a parameter
    public double findVariation(TradeModel tradeModel){
        double variation = (tradeModel.getPrice()*tradeModel.getQuantity())-(tradeModel.getBuyingPrice()*tradeModel.getQuantity());
        if(variation>0){
            return Double.parseDouble("+"+variation);
        }else if(variation==00.00){
            return variation;
        }else{
            return Double.parseDouble("-"+variation);
        }
    }
}
