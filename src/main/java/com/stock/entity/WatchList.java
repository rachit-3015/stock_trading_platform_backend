package com.stock.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WatchList {
    @Id
    private String email;

    private String symbol;
}

