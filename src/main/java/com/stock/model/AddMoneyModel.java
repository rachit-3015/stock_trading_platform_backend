package com.stock.model;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddMoneyModel {
    private String email;
    private double balance;
}
