package com.stock.config;

import com.stock.service.PortfolioService;
import com.stock.service.impl.PortfolioServiceImpl;
import com.stock.twelvedata.service.TwelveDataApiClient;
import com.stock.twelvedata.service.impl.TwelveDataApiClientImpl;
import okhttp3.OkHttpClient;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}
