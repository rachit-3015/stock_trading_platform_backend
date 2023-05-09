package com.stock.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HistoryModel {
    private String email;
    private String symbol;
    private double buyingPrice;
    private double sellingPrice;
    private double quantity;
    private String profitLoss;
    private double variation;
}
