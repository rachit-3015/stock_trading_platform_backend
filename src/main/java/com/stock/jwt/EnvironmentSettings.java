package com.stock.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import org.springframework.core.env.Environment;

@Getter
@Setter
@Component
public class EnvironmentSettings {
    private String jwtTokenExp;
    private String jwtSecretKey;
    private String refreshTokenExpiry;

    public EnvironmentSettings(Environment env) {
        jwtTokenExp = env.getProperty("jwt.token.expiry");
        jwtSecretKey = env.getProperty("jwt.secret.key");
        refreshTokenExpiry = env.getProperty("jwt.token.refresh.expiry");
    }
}
