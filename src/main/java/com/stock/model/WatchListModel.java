package com.stock.model;


import lombok.*;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WatchListModel {
    private String email;
    private String symbol;
}
