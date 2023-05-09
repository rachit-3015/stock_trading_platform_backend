package com.stock.repository;

import com.stock.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository <History,String> {
    List<History> getAllByEmail(String email);
}
