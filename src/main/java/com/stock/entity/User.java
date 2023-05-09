package com.stock.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    private String name;
    @Id
    private String email;
    private String password;

}
