package com.stock.model;

import lombok.*;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterModel {
    private String name;
    private String email;
    private String password;
}
