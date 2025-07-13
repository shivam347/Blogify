package com.example.SpringStarter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.SpringStarter.Service.PostService;


@Controller
public class HomeController {

    @Autowired
    private PostService postService;

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("posts", postService.getAll());
        return "home";
    }


    

  
    
}



/*
@Controller
This is a Spring annotation that tells Spring that this class is a web controller.

It is used to handle web requests and return views (HTML pages).

🔸 public class HomeController
This is a Java class named HomeController which acts as the controller layer in the MVC (Model-View-Controller) pattern.

🔸 @GetMapping("/home")
This means: When the user sends a GET request to /home (like visiting http://localhost:8080/home), this method should handle the request.

It maps the URL /home to the home() method.

🔸 public String home(Model model)
This method returns a String, which is the name of the view (HTML page) to be shown.

Model model is used to pass data from controller to the view (HTML). Though in this code, no data is added to the model.

🔸 return "home";
This tells Spring to render a view named home (like home.html or home.jsp located in src/main/resources/templates/ if using Thymeleaf).

 */
