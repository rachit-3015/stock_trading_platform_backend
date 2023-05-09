package com.stock.service;

import com.stock.model.AddMoneyModel;
import org.springframework.stereotype.Service;

@Service
public interface AddMoneyService {
    double getCurrentBalance(String email) ;
    void addMoney(AddMoneyModel addMoneyModel);
    void setAddBalance(double transactionDetail,String email);
    void setSubstractBalance(double transactionDetail,String email);

}
