package com.stock.twelvedata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FarragoOfData {
    private double price;
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
    private Meta meta;
    private List<Values> values;
}
