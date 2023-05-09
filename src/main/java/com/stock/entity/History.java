package com.stock.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class History {
    @Id
    private String email;

    private String symbol;
    private double buyingPrice;
    private double sellingPrice;
    private double quantity;
    private String profitLoss;
    private double variation;

}
