package com.stock.repository;

import com.stock.entity.AddMoney;
import com.stock.model.AddMoneyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddMoneyRepository extends JpaRepository<AddMoney,String> {
    AddMoney getByEmail(String email);
}
