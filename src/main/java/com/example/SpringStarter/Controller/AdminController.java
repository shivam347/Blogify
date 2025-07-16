package com.example.SpringStarter.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminController {
    /**
     * Handles requests to the admin page.
     * 
     * @param model the model to be used in the view
     * @return the name of the admin view template
     */
    @GetMapping("/admin")
    public String adminPage(Model model){
        return "admin";
    }

    
}
