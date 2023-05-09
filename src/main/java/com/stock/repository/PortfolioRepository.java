package com.stock.repository;

import com.stock.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio,String> {
    List<Portfolio> getAllByEmail(String email);
    @Query("select p from  Portfolio p where p.email=:email and p.symbol=:symbol and p.quantity=:quantity")
    Portfolio getParticularPortfolio(String email,String symbol,double quantity);
}
