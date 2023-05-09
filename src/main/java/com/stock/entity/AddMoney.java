package com.stock.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddMoney {
    @Id
    private String email;

    private double balance;
}
