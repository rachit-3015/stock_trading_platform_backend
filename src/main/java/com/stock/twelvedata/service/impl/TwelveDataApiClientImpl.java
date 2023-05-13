package com.stock.twelvedata.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.twelvedata.model.*;
import com.stock.twelvedata.service.TwelveDataApiClient;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TwelveDataApiClientImpl implements TwelveDataApiClient {
    private static final String BASE_URL = "";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public TwelveDataApiClientImpl() {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override//this is not in use
    public StockData getQuoteData(String symbol) throws IOException {

        String url = String.format("%squote?symbol=%s&apikey=%s", BASE_URL, symbol, "");

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode dataNode = rootNode.path("values");



            StockData stockData = new StockData();
            stockData.setSymbol(rootNode.path("symbol").asText());
            stockData.setName(rootNode.path("name").asText());
            stockData.setExchange(rootNode.path("exchange").asText());
            stockData.setMic_code(rootNode.path("mic_code").asText());
            stockData.setCurrency(rootNode.path("currency").asText());
            stockData.setDatetime(rootNode.path("datetime").asText());
            stockData.setTimestamp(rootNode.path("timestamp").asLong());
            stockData.setOpen(rootNode.path("open").asDouble());
            stockData.setHigh(rootNode.path("high").asDouble());
            stockData.setLow(rootNode.path("low").asDouble());
            stockData.setClose(rootNode.path("close").asDouble());
            stockData.setVolume(rootNode.path("volume").asLong());
            stockData.setPrevious_close(rootNode.path("previous_close").asDouble());
            stockData.setChange(rootNode.path("change").asDouble());
            stockData.setPercent_change(rootNode.path("percent_change").asDouble());
            stockData.setAverage_volume(rootNode.path("average_volume").asLong());
            stockData.set_market_open(rootNode.path("is_market_open").asBoolean());

            FiftyTwoWeek fiftyTwoWeek = new FiftyTwoWeek();
            JsonNode fiftyTwoWeekNode = rootNode.path("fifty_two_week");
            fiftyTwoWeek.setLow(fiftyTwoWeekNode.path("low").asDouble());
            fiftyTwoWeek.setHigh(fiftyTwoWeekNode.path("high").asDouble());
            fiftyTwoWeek.setLow_change(fiftyTwoWeekNode.path("low_change").asDouble());
            fiftyTwoWeek.setHigh_change(fiftyTwoWeekNode.path("high_change").asDouble());
            fiftyTwoWeek.setLow_change_precent(fiftyTwoWeekNode.path("low_change_precent").asDouble());
            fiftyTwoWeek.setHigh_change_precent(fiftyTwoWeekNode.path("high_change_precent").asDouble());
            fiftyTwoWeek.setRange(fiftyTwoWeekNode.path("range").asText());
            stockData.setFiftyTwoWeek(fiftyTwoWeek);

            return stockData;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override//this is not in use
    public LiveStockPrice getLiveStockPrice(String symbol) throws IOException {
        String url = String.format("%sprice?symbol=%s&interval=1min&apikey=%s", BASE_URL, symbol, "");

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            JsonNode rootNode = objectMapper.readTree(json);


            LiveStockPrice liveStockPrice = new LiveStockPrice();
            liveStockPrice.setPrice(rootNode.path("price").asDouble());

            return liveStockPrice;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override//this is not in use
    public AllTimeSeriesStock getAllTimeSeriesStock() throws IOException {
        List<String> symbols = List.of("TSLA","GS","JPM","MS","GNENF","WFC","BAC");
        String symbolSep = String.join(",", symbols);
        String url = String.format("%time_series?symbol=%s&apikey=%s&outputsize=1", BASE_URL, symbolSep, "");
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            JsonNode rootNode = objectMapper.readTree(json);

            AllTimeSeriesStock timeSeriesStock = new AllTimeSeriesStock();


            Map<String, Meta> metaMap = new HashMap<>();
            for (String symbol : symbols) {
                JsonNode metaNode = rootNode.path(symbol).path("meta");
                Meta meta = new Meta();
                meta.setSymbol(metaNode.path("symbol").asText());
                meta.setInterval(metaNode.path("interval").asText());
                meta.setCurrency(metaNode.path("currency").asText());
                meta.setExchange_timezone(metaNode.path("exchange_timezone").asText());
                meta.setExchange(metaNode.path("exchange").asText());
                meta.setMic_code(metaNode.path("mic_code").asText());
                meta.setType(metaNode.path("type").asText());
                metaMap.put(symbol, meta);
            }
            timeSeriesStock.setMeta(metaMap);


            Map<String, List<Values>> valuesMap = new HashMap<>();
            for (String symbol : symbols) {
                List<Values> valuesList = new ArrayList<>();
                JsonNode valuesNode = rootNode.path(symbol).path("values");
                if (valuesNode.isArray()) {
                    for (JsonNode valueNode : valuesNode) {
                        Values values = new Values();
                        values.setDatetime(valueNode.path("datetime").asText());
                        values.setOpen(valueNode.path("open").asDouble());
                        values.setHigh(valueNode.path("high").asDouble());
                        values.setLow(valueNode.path("low").asDouble());
                        values.setClose(valueNode.path("close").asDouble());
                        values.setVolume(valueNode.path("volume").asLong());
                        valuesList.add(values);
                    }
                }
                valuesMap.put(symbol, valuesList);
            }
            timeSeriesStock.setValues(valuesMap);

            return timeSeriesStock;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override//this is not in use
    public List<StockData> getAllQuoteData() throws IOException {
        List<String> symbols = List.of("C","GS","ATCO","JPM","MS","SPGI","WFC","BAC");

        List<StockData> stockDataList = new ArrayList<>();

        for(String symbol : symbols) {
            String url = String.format("%squote?symbol=%s&apikey=%s", BASE_URL, symbol, "");

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String json = response.body().string();
                JsonNode rootNode = objectMapper.readTree(json);
                JsonNode dataNode = rootNode.path("values");

                StockData stockData = new StockData();
                stockData.setSymbol(rootNode.path("symbol").asText());
                stockData.setName(rootNode.path("name").asText());
                stockData.setExchange(rootNode.path("exchange").asText());
                stockData.setMic_code(rootNode.path("mic_code").asText());
                stockData.setCurrency(rootNode.path("currency").asText());
                stockData.setDatetime(rootNode.path("datetime").asText());
                stockData.setTimestamp(rootNode.path("timestamp").asLong());
                stockData.setOpen(rootNode.path("open").asDouble());
                stockData.setHigh(rootNode.path("high").asDouble());
                stockData.setLow(rootNode.path("low").asDouble());
                stockData.setClose(rootNode.path("close").asDouble());
                stockData.setVolume(rootNode.path("volume").asLong());
                stockData.setPrevious_close(rootNode.path("previous_close").asDouble());
                stockData.setChange(rootNode.path("change").asDouble());
                stockData.setPercent_change(rootNode.path("percent_change").asDouble());
                stockData.setAverage_volume(rootNode.path("average_volume").asLong());
                stockData.set_market_open(rootNode.path("is_market_open").asBoolean());


                FiftyTwoWeek fiftyTwoWeek = new FiftyTwoWeek();
                JsonNode fiftyTwoWeekNode = rootNode.path("fifty_two_week");
                fiftyTwoWeek.setLow(fiftyTwoWeekNode.path("low").asDouble());
                fiftyTwoWeek.setHigh(fiftyTwoWeekNode.path("high").asDouble());
                fiftyTwoWeek.setLow_change(fiftyTwoWeekNode.path("low_change").asDouble());
                fiftyTwoWeek.setHigh_change(fiftyTwoWeekNode.path("high_change").asDouble());
                fiftyTwoWeek.setLow_change_precent(fiftyTwoWeekNode.path("low_change_precent").asDouble());
                fiftyTwoWeek.setHigh_change_precent(fiftyTwoWeekNode.path("high_change_precent").asDouble());
                fiftyTwoWeek.setRange(fiftyTwoWeekNode.path("range").asText());
                stockData.setFiftyTwoWeek(fiftyTwoWeek);

                stockDataList.add(stockData);
            }catch(Exception  e) {
                e.printStackTrace();
            }
        }
        return stockDataList;
    }

    @Override
    public FarragoOfData getFarragoOfData(String symbol) throws IOException {

        String quoteUrl = String.format("%squote?symbol=%s&apikey=%s", BASE_URL, symbol, "2fc75b90630244cdbdf0d59852f0dc65");
        String timeSeriesUrl = String.format("%stime_series?symbol=%s&interval=1min&apikey=%s", BASE_URL, symbol, "");
        String livePriceUrl = String.format("%sprice?symbol=%s&interval=1min&apikey=%s",BASE_URL, symbol, "");

        Request quoteRequest = new Request.Builder()
                .url(quoteUrl)
                .build();
        Request timeSeriesRequest = new Request.Builder()
                .url(timeSeriesUrl)
                .build();
        Request livePriceRequest = new Request.Builder()
                .url(livePriceUrl)
                .build();

        FarragoOfData farragoOfData = new FarragoOfData();

        try (Response quoteResponse = client.newCall(quoteRequest).execute();
             Response timeSeriesResponse = client.newCall(timeSeriesRequest).execute();
             Response livePriceResponse = client.newCall(livePriceRequest).execute())	{

            String jsonLivePrice = livePriceResponse.body().string();
            JsonNode livePriceNode = objectMapper.readTree(jsonLivePrice);

            farragoOfData.setPrice(livePriceNode.path("price").asDouble());

            String jsonQuote = quoteResponse.body().string();
            JsonNode quoteNode = objectMapper.readTree(jsonQuote);

            farragoOfData.setSymbol(quoteNode.path("symbol").asText());
            farragoOfData.setName(quoteNode.path("name").asText());
            farragoOfData.setExchange(quoteNode.path("exchange").asText());
            farragoOfData.setMic_code(quoteNode.path("mic_code").asText());
            farragoOfData.setCurrency(quoteNode.path("currency").asText());
            farragoOfData.setDatetime(quoteNode.path("datetime").asText());
            farragoOfData.setTimestamp(quoteNode.path("timestamp").asLong());
            farragoOfData.setOpen(quoteNode.path("open").asDouble());
            farragoOfData.setHigh(quoteNode.path("high").asDouble());
            farragoOfData.setLow(quoteNode.path("low").asDouble());
            farragoOfData.setClose(quoteNode.path("close").asDouble());
            farragoOfData.setVolume(quoteNode.path("volume").asLong());
            farragoOfData.setPrevious_close(quoteNode.path("previous_close").asDouble());
            farragoOfData.setChange(quoteNode.path("change").asDouble());
            farragoOfData.setPercent_change(quoteNode.path("percent_change").asDouble());
            farragoOfData.setAverage_volume(quoteNode.path("average_volume").asLong());
            farragoOfData.set_market_open(quoteNode.path("is_market_open").asBoolean());

            FiftyTwoWeek fiftyTwoWeek = new FiftyTwoWeek();
            JsonNode fiftyTwoWeekNode = quoteNode.path("fifty_two_week");
            fiftyTwoWeek.setLow(fiftyTwoWeekNode.path("low").asDouble());
            fiftyTwoWeek.setHigh(fiftyTwoWeekNode.path("high").asDouble());
            fiftyTwoWeek.setLow_change(fiftyTwoWeekNode.path("low_change").asDouble());
            fiftyTwoWeek.setHigh_change(fiftyTwoWeekNode.path("high_change").asDouble());
            fiftyTwoWeek.setLow_change_precent(fiftyTwoWeekNode.path("low_change_precent").asDouble());
            fiftyTwoWeek.setHigh_change_precent(fiftyTwoWeekNode.path("high_change_precent").asDouble());
            fiftyTwoWeek.setRange(fiftyTwoWeekNode.path("range").asText());
            farragoOfData.setFiftyTwoWeek(fiftyTwoWeek);

            String jsonTimeSeries = timeSeriesResponse.body().string();
            System.out.println(jsonTimeSeries);
            JsonNode timeSeriesNode = objectMapper.readTree(jsonTimeSeries);
            JsonNode metaNode = timeSeriesNode.path("meta");
            Meta meta = new Meta();
            meta.setSymbol(metaNode.path("symbol").asText());
            meta.setInterval(metaNode.path("interval").asText());
            meta.setCurrency(metaNode.path("currency").asText());
            meta.setExchange_timezone(metaNode.path("exchange_timezone").asText());
            meta.setExchange(metaNode.path("exchange").asText());
            meta.setMic_code(metaNode.path("mic_code").asText());
            meta.setType(metaNode.path("type").asText());
            farragoOfData.setMeta(meta);

            List<Values> valuesList = new ArrayList<>();
            JsonNode valuesNode = timeSeriesNode.path("values");
            System.out.println(valuesNode);
            if (valuesNode.isArray()) {
                for (JsonNode valueNode : valuesNode) {
                    Values values = new Values();
                    values.setDatetime(valueNode.path("datetime").asText());
                    values.setOpen(valueNode.path("open").asDouble());
                    values.setHigh(valueNode.path("high").asDouble());
                    values.setLow(valueNode.path("low").asDouble());
                    values.setClose(valueNode.path("close").asDouble());
                    values.setVolume(valueNode.path("volume").asLong());
                    valuesList.add(values);
                }
                farragoOfData.setValues(valuesList);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return farragoOfData;
    }

    @Override
    public List<HomeScreen> getHomeScreenData() throws IOException {
        List<String> symbols = List.of("C","GS","G","JPM","MS","SPGI","WFC","BAC");

        List<HomeScreen> homeScreenList = new ArrayList<>();

        for(String symbol : symbols) {
            String livePriceUrl = String.format("%sprice?symbol=%s&interval=1min&apikey=%s",BASE_URL, symbol, "");
            String quoteUrl = String.format("%squote?symbol=%s&apikey=%s", BASE_URL, symbol, "");

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
