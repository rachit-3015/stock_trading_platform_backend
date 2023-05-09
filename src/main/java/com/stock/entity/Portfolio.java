package com.stock.entity;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {
    @Id
    private String email;

    private String symbol;
    private double buyingPrice;
    @Nullable
    private double sellingPrice;
    private double quantity;
    private double latestPrice;
    private String profitLoss;
    private double variation;
}
