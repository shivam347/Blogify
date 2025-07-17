package com.example.SpringStarter.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public String getPost(@PathVariable Long id, Model model, Principal principal, Authentication authentication) {
        // Fetch the post by ID using PostService
        // If the post is found, add it to the model and return the view name
        Optional<Post> post = postService.getById(id);

        if (post.isPresent()) {
            model.addAttribute("post", post.get()); // Add the post to the model

            boolean isAuthor = false;
            boolean canEditAll = false;

            // Check if the authenticated user is the author of the post
            if (authentication != null && principal != null) {

                String userEmail = principal.getName();

                if (post.get().getAccount() != null && post.get().getAccount().getEmail().equals(userEmail)) {
                    isAuthor = true; // User is the author of the post
                }

                // Check if the user has admin or editor role
                canEditAll = authentication.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")
                                || auth.getAuthority().equals("ROLE_EDITOR"));
            }

            model.addAttribute("isAuthor", isAuthor); // Add author status to the model
             model.addAttribute("canEditAll", canEditAll); // Add edit permission status to the model

            // If the user is the author or has admin/editor role, allow editing useful for the UI
            model.addAttribute("canEditAll", isAuthor || canEditAll); // Add edit permission status to the model
            return "post_views/post"; // Return the view template name for displaying the post
        } else {
            model.addAttribute("errorMessage", "Post not found"); // Add error message if post not found
            return "error/404"; // Redirect to a 404 error page if post not found

        }

    }


    @GetMapping("/add_post")
    public String addPost(Model model) {
        // Return the view template name for adding a post
        // This method can be used to display a form for creating a new post
        return "post_views/add_post"; // Return the view template name for adding a post
    }
   
}
