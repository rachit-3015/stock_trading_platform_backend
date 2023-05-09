package com.stock.model;

import lombok.*;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioModel {
    private String email;
    private double price;
    private String symbol;
    private double buyingPrice;
    private double sellingPrice;
    private double quantity;
    private double latestPrice;
    private String profitLoss;
    private double variation;
}
