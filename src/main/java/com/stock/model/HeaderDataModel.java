package com.stock.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HeaderDataModel {
    private String email;
    private String jwtToken;
}
