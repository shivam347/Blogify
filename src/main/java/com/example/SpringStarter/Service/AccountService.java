package com.example.SpringStarter.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.SpringStarter.Models.Account;
import com.example.SpringStarter.Repositories.AccountRepository;
import com.example.SpringStarter.Util.Constants.Roles;


/**
 * Service class for managing Account entities.
 * Provides methods for creating, updating, deleting, and retrieving accounts.
 * Interacts with AccountRepository to perform database operations.
 */
@Service
public class AccountService implements UserDetailsService {

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
        // Only encode if not already encoded (simple check for BCrypt)
        
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if(account.getRole() == null){
        account.setRole(Roles.USER.getRole()); // Set default user role for not defined role accounts
        }
        
        return accountRepository.save(account);
    }

    /**
     * Loads the user by username for Spring Security authentication.
     * @param username The username to search for.
     * @return The UserDetails object for authentication.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findOneByEmailIgnoreCase(email);

        if(!optionalAccount.isPresent()){
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        Account account = optionalAccount.get();
        

        List<GrantedAuthority> grantedAuthority = new ArrayList<>();
        grantedAuthority.add(() -> account.getRole());      
        return new User(
            account.getEmail(),
            account.getPassword(),
            grantedAuthority
        );
        

      
    }

}
