package com.stock.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
    @Id
    private String email;

    private String symbol;
    private String forBuySell;
    private double buyingPrice;
    private double sellingPrice;
    private double quantity;
}
