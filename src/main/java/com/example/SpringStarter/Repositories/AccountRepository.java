package com.example.SpringStarter.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringStarter.Models.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    /**
     * Finds an account by its email, ignoring case.
     * 
     * @param email the email to search for
     * @return an Optional containing the Account if found, or empty if not found
     */
    Optional<Account> findOneByEmailIgnoreCase(String email);

    Optional<Account> findByPasswordResetToken(String token);

    Optional<Account> findById(long id);


} 
    

