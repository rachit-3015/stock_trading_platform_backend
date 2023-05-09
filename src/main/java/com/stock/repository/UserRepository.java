package com.stock.repository;

import com.stock.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    @Query("select u from User u where u.email = :email")
    User getUserByUsername(@Param("email") String email);
}
