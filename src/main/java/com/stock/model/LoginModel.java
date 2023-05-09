package com.stock.model;

import lombok.*;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginModel {
    private String email;
    private String password;
}
