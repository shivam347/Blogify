package com.example.SpringStarter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.SpringStarter.Models.Account;
import com.example.SpringStarter.Service.AccountService;

/**
 * AccountController handles user registration and login page requests.
 */
@Controller
public class AccountController {

    // Inject AccountService to handle account-related operations
    @Autowired
    private AccountService accountService;

    /**
     * Handles GET requests for the registration page.
     * Adds a new Account object to the model for form binding.
     * @param model The model to pass data to the view.
     * @return The registration page template name.
     */
    @GetMapping("/register")
    public String register(Model model){
        Account account = new Account(); // Create a new empty account
        model.addAttribute("account", account); // Add account to the model
        return "register"; // Return the registration view
    }

    /**
     * Handles POST requests for user registration.
     * Saves the submitted account data using AccountService.
     * @param account The account object populated from the form.
     * @return Redirects to the home page after successful registration.
     */
    @PostMapping("/register")
    public String registerAccount(@ModelAttribute Account account){
        accountService.save(account); // Save the new account
        return "redirect:/home"; // Redirect to home page
    }

    /**
     * Handles GET requests for the login page.
     * @param model The model to pass data to the view (not used here).
     * @return The login page template name.
     */
    @GetMapping("/login")
    public String login(Model model){
        return "login"; // Return the login view
    }
    
}
