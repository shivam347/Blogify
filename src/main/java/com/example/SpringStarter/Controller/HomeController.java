package com.example.SpringStarter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.SpringStarter.Service.PostService;

/**
 * HomeController handles requests for the home page.
 * It retrieves all posts and passes them to the view for display.
 */
@Controller // Marks this class as a Spring MVC controller
public class HomeController {

    // Inject PostService to access post-related operations
    @Autowired
    private PostService postService;

    /**
     * Handles GET requests to "/home".
     * Adds all posts to the model so they can be displayed in the view.
     * @param model The model to pass data to the view.
     * @return The name of the view template ("home").
     */
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("posts", postService.getAll()); // Add all posts to the model
        return "home"; // Return the view template name
    }

}

