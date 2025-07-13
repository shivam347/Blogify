package com.example.SpringStarter.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.SpringStarter.Models.Account;

@Controller
public class AccountController {

    public String register(Model model){
       
        Account account = new Account();
        model.addAttribute("account", account);
        return "register";

    }
    
}
