package com.stock.twelvedata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FiftyTwoWeek {
    private double low;
    private double high;
    private double low_change;
    private double high_change;
    private double low_change_precent;
    private double high_change_precent;
    private String range;
}
