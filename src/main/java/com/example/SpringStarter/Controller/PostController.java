package com.example.SpringStarter.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.SpringStarter.Models.Account;
import com.example.SpringStarter.Models.Post;
import com.example.SpringStarter.Service.AccountService;
import com.example.SpringStarter.Service.PostService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

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

            // If the user is the author or has admin/editor role, allow editing useful for
            // the UI
            model.addAttribute("canEditAll", isAuthor || canEditAll); // Add edit permission status to the model
            return "post_views/post"; // Return the view template name for displaying the post
        } else {
            model.addAttribute("errorMessage", "Post not found"); // Add error message if post not found
            return "error/404"; // Redirect to a 404 error page if post not found

        }

    }

    @GetMapping("/add_post")
    @PreAuthorize("isAuthenticated()") // Ensure the user is authenticated before accessing this method
    public String addPost(Model model, Principal principal) {
        String authUser = "email"; // Default email if principal is null
        if (principal != null) {
            authUser = principal.getName(); // Get the email from the Principal object
        }

        Optional<Account> optionalaccount = accountService.getByEmail(authUser);
        if (optionalaccount.isPresent()) {
            Post post = new Post();
            post.setAccount(optionalaccount.get()); // Set the account for the post
            model.addAttribute("post", post); // Add the post to the model for form binding
            return "post_views/add_post"; // Return the view template name for adding a post
        } else {
            return "redirect:/login"; // Redirect to login if the account is not found
        }

    }

    // Method to handle the form submission for adding a new post
    // It checks if the authenticated user is the owner of the account associated
    // with the post

    @PostMapping("/post/add")
    @PreAuthorize("isAuthenticated()")
    public String addPostHandler(@ModelAttribute Post post, Principal principal) {
        String authUser = "email"; // Default email if principal is null

        if (principal != null) {
            authUser = principal.getName(); // Get the email from the Principal object
        }

        if (post.getAccount().getEmail().compareToIgnoreCase(authUser) < 0) {
            return "redirect:/login?error"; // Redirect to login if the account email does not match
        }

        postService.save(post); // Save the post using PostService
        return "redirect:/posts/" + post.getId(); // Redirect to the newly created post

    }

    @GetMapping("/post/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostforEdit(@PathVariable long id, Model modle) {
        Optional<Post> post = postService.getById(id); // Fetch the post by ID using PostService
        if (post.isPresent()) {
            modle.addAttribute("post", post.get()); // Add the post to the model for editing
            return "post_views/edit_post"; // Return the view template name for editing a post

        } else {
            modle.addAttribute("errorMessage", "Post not found"); // Add error message if post not found
            return "error/404"; // Redirect to a 404 error page if post not found
        }
    }

    @PostMapping("/post/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String updatePostHandler(@PathVariable long id, @ModelAttribute Post post) {

        Optional<Post> existingPost = postService.getById(id); // Fetch the existing post by ID
        if (existingPost.isPresent()) {
            Post updatedPost = existingPost.get(); // Get the existing post object
            updatedPost.setTitle(post.getTitle()); // Update the title
            updatedPost.setBody(post.getBody()); // Update the body
            postService.save(updatedPost); // Save the updated post
            return "redirect:/posts/" + updatedPost.getId(); // Redirect to the updated post
        } else {
            return "error/404"; // Redirect to a 404 error page if post not found
        }
    }


    @GetMapping("/post/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String deletePost(@PathVariable long id) {
        Optional<Post> post = postService.getById(id); // Fetch the post by ID
        if (post.isPresent()) {
            postService.delete(post.get()); // Delete the post using PostService
            return "redirect:/home"; // Redirect to the home page after deletion
        } else {
            return "error/404"; // Redirect to a 404 error page if post not found
        }
    }

}
