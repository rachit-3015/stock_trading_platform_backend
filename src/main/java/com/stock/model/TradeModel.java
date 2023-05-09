package com.stock.model;

import lombok.*;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeModel {
    private String email;
    private String symbol;
    private double price;
    private String forBuySell;
    private double buyingPrice;
    private double sellingPrice;
    private double quantity;
}
