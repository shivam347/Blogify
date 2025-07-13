package com.example.SpringStarter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringStarter.Models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

} 
    

