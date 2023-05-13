package com.stock.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.entity.WatchList;
import com.stock.exception.ResourceNotFoundInWatchlist;
import com.stock.model.WatchListModel;
import com.stock.repository.WatchListRepository;
import com.stock.service.WatchListService;
import com.stock.twelvedata.model.HomeScreen;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WatchListServiceImpl implements WatchListService {

    @Autowired
    private WatchListRepository watchListRepository;
    @Autowired
    private ModelMapper modelMapper;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private static final String BASE_URL = "";

    public WatchListServiceImpl() {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void addToWatchlist(WatchListModel watchListModel) {
        WatchList watchList = modelMapper.map(watchListModel,WatchList.class);
        this.watchListRepository.save(watchList);
    }

    @Override
    public void removeFromWatchlist(WatchListModel watchListModel) throws ResourceNotFoundInWatchlist {
        WatchList watchList = this.watchListRepository.getWatchListBySymbol(watchListModel.getSymbol());
        if(watchList == null){
            throw new ResourceNotFoundInWatchlist("No such stocks are available in Watchlist");
        }
        this.watchListRepository.deleteWatchListBySymbol(watchListModel.getSymbol());
    }

    @Override
    public List<HomeScreen> getAllWatchlist(String email) {
        List<WatchList> allWatchList= this.watchListRepository.getAllByEmail(email);

        List<HomeScreen> homeScreenList = new ArrayList<>();

        for(WatchList watchList : allWatchList) {
            String livePriceUrl = String.format("%sprice?symbol=%s&interval=1min&apikey=%s",BASE_URL, watchList.getSymbol(), "");
            String quoteUrl = String.format("%squote?symbol=%s&apikey=%s", BASE_URL, watchList.getSymbol(), "");

            HomeScreen homeScreen = new HomeScreen();

            Request livePriceRequest = new Request.Builder()
                    .url(livePriceUrl)
                    .build();

            Request quoteRequest = new Request.Builder()
                    .url(quoteUrl)
                    .build();
            try (Response quoteResponse = client.newCall(quoteRequest).execute();
                 Response livePriceResponse = client.newCall(livePriceRequest).execute()){

                String jsonLivePrice = livePriceResponse.body().string();
                JsonNode livePriceNode = objectMapper.readTree(jsonLivePrice);

                homeScreen.setPrice(livePriceNode.path("price").asDouble());

                String jsonQuote = quoteResponse.body().string();
                JsonNode quoteNode = objectMapper.readTree(jsonQuote);

                homeScreen.setOpen(quoteNode.path("open").asDouble());
                homeScreen.setHigh(quoteNode.path("high").asDouble());
                homeScreen.setLow(quoteNode.path("low").asDouble());
                homeScreen.setPrevious_close(quoteNode.path("previous_close").asDouble());
                homeScreen.setPercent_change(quoteNode.path("percent_change").asDouble());
                homeScreen.setChange(quoteNode.path("change").asDouble());
                homeScreen.setSymbol(quoteNode.path("symbol").asText());

                homeScreenList.add(homeScreen);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return homeScreenList;
    }
}
