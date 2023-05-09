package com.stock.service.impl;

import com.stock.entity.AddMoney;
import com.stock.model.AddMoneyModel;
import com.stock.repository.AddMoneyRepository;
import com.stock.service.AddMoneyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddMoneyServiceImpl implements AddMoneyService {
    @Autowired
    private AddMoneyRepository addMoneyRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public double getCurrentBalance(String email) {
        System.out.println(email);
        AddMoney addMoney = this.addMoneyRepository.getByEmail(email);
        if(addMoney==null){
            System.out.println("MoneyNotFoundException");
            return 0;
        }
        return addMoney.getBalance();
    }

    @Override
    public void addMoney(AddMoneyModel addMoneyModel) {
        AddMoney obj = this.addMoneyRepository.getByEmail(addMoneyModel.getEmail());
        if(obj!=null){
            addMoneyModel.setBalance(addMoneyModel.getBalance()+ obj.getBalance());
        }
        AddMoney addMoney = modelMapper.map(addMoneyModel,AddMoney.class);
        this.addMoneyRepository.save(addMoney);
    }

    @Override
    public void setAddBalance(double transactionDetail, String email) {
        AddMoney addMoney = this.addMoneyRepository.getByEmail(email);
        addMoney.setBalance(transactionDetail+addMoney.getBalance());
    }

    @Override
    public void setSubstractBalance(double transactionDetail, String email) {
        AddMoney addMoney = this.addMoneyRepository.getByEmail(email);
        addMoney.setBalance(addMoney.getBalance()-transactionDetail);
    }

}
