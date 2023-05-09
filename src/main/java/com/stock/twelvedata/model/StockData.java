package com.stock.twelvedata.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockData {
    private String symbol;
    private String name;
    private String exchange;
    private String mic_code;
    private String currency;
    private String datetime;
    private long timestamp;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;
    private double previous_close;
    private double change;
    private double percent_change;
    private long average_volume;
    private boolean is_market_open;
    private FiftyTwoWeek fiftyTwoWeek;
}
