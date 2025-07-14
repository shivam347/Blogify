package com.example.SpringStarter.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SpringStarter.Models.Account;
import com.example.SpringStarter.Repositories.AccountRepository;

/**
 * Service class for managing Account entities.
 * Provides methods for creating, updating, deleting, and retrieving accounts.
 * Interacts with AccountRepository to perform database operations.
 */
@Service
public class AccountService {

    /**
     * Injected AccountRepository for database operations.
     */
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Saves a new or existing Account entity to the database.
     * @param account The Account object to save.
     * @return The saved Account entity.
     */
    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        // If the account already exists, it will be updated; otherwise, a new account will be created.);
        return accountRepository.save(account);
    }

}
