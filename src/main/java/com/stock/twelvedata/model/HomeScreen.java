package com.stock.twelvedata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeScreen {
    private String symbol;
    private double price;
    private double change;
    private double percent_change;
    private double open;
    private double high;
    private double low;
    private double previous_close;
}
