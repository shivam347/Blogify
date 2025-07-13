package com.example.SpringStarter.Service;

import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Saves a new or existing Account entity to the database.
     * @param account The Account object to save.
     * @return The saved Account entity.
     */
    public Account save(Account account) {
        return accountRepository.save(account);
    }

}
