package com.example.SpringStarter.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.SpringStarter.Models.Post;
import com.example.SpringStarter.Service.PostService;


@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        // Fetch the post by ID using PostService
        // If the post is found, add it to the model and return the view name
        Optional<Post> post = postService.getById(id);
        
        if(post.isPresent()){
            model.addAttribute("post", post.get()); // Add the post to the model
            return "post_views/post"; // Return the view template name for displaying the post
        } else {
            model.addAttribute("errorMessage", "Post not found"); // Add error message if post not found
              return "error/404"; // Redirect to a 404 error page if post not found        

        }
       
    }

}
    
