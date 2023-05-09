package com.stock.twelvedata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {
    private String symbol;
    private String interval;
    private String currency;
    private String exchange_timezone;
    private String exchange;
    private String mic_code;
    private String type;
}
