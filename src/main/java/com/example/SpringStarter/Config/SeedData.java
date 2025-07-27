package com.example.SpringStarter.Config;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.SpringStarter.Models.Account;
import com.example.SpringStarter.Models.Authority;
import com.example.SpringStarter.Models.Post;
import com.example.SpringStarter.Service.AccountService;
import com.example.SpringStarter.Service.AuthorityService;
import com.example.SpringStarter.Service.PostService;
import com.example.SpringStarter.Util.Constants.Privileges;
import com.example.SpringStarter.Util.Constants.Roles;

/**
 * SeedData class is used to populate the database with initial data
 * when the application starts. Implements CommandLineRunner so that
 * the run() method is executed at startup.
 */
@Component
public class SeedData implements CommandLineRunner {

    // Inject PostService to manage Post entities
    @Autowired
    private PostService postService;

    // Inject AccountService to manage Account entities
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AuthorityService authorityService;

    /**
     * This method runs at application startup and seeds the database
     * with sample accounts and posts if no posts exist.
     */
    @Override
    public void run(String... args) throws Exception {

        for(Privileges auth : Privileges.values()){
            Authority authority = new Authority(auth.getAuthorityName());
            authorityService.save(authority);

        }
        

        // Create two sample Account objects
        Account account01 = new Account();
        Account account02 = new Account();
        Account account03 = new Account();

        // Set properties for the first account
        account01.setUsername("vidhi");
        account01.setPassword("editor123"); // Password will be encoded in AccountService
        account01.setEmail("editor123@gmail.com");
        account01.setRole(Roles.EDITOR.getRole());
        account01.setGender("Female");
        account01.setDate_of_birth(LocalDate.parse("1990-03-01")); // Set date of birth

        // Set properties for the second account
        account02.setUsername("shivam");
        account02.setPassword("admin123"); // Password will be encoded in AccountService
        account02.setEmail("shivamfeb77@gmail.com");
        account02.setRole(Roles.ADMIN.getRole());
        account02.setGender("Male");
        account02.setDate_of_birth(LocalDate.parse("1995-06-01"));


        // Set properties for the third account
        account03.setUsername("vaibhav");
        account03.setPassword("user123"); // Password will be encoded in AccountService
        account03.setEmail("user123@gmail.com");
        account03.setGender("Male");
        account03.setDate_of_birth(LocalDate.parse("2000-05-01"));

        Set<Authority> authorities = new HashSet<>();

        authorityService.findById(Privileges.ACCES_ADMIN_PANEL.getAuthorityId()).ifPresent(authorities::add);
        authorityService.findById(Privileges.RESET_USER_PASSWORD.getAuthorityId()).ifPresent(authorities::add);
        account03.setAuthorities(authorities);  
        



        // Save the accounts to the database
        accountService.save(account01);
        accountService.save(account02);
        accountService.save(account03);

        // Retrieve all posts from the database
        List<Post> posts = postService.getAll();

        // If there are no posts, create and save two sample posts
        if(posts.size() == 0){
            // Create first post and associate with account01
            Post post01 = new Post();
            post01.setTitle("post01 title");
            post01.setBody("Building REST services with Spring\r\n" + //
                                "REST has quickly become the de facto standard for building web services on the web because REST services are easy to build and easy to consume.\r\n" + //
                                "\r\n" + //
                                "A much larger discussion can be had about how REST fits in the world of microservices. However, for this tutorial, we look only at building RESTful services.\r\n" + //
                                "\r\n" + //
                                "Why REST? REST embraces the precepts of the web, including its architecture, benefits, and everything else. This is no surprise, given that its author (Roy Fielding) was involved in probably a dozen specs which govern how the web operates.\r\n" + //
                                "\r\n" + //
                                "What benefits? The web and its core protocol, HTTP, provide a stack of features:\r\n" + //
                                "\r\n" + //
                                "Suitable actions (GET, POST, PUT, DELETE, and others)\r\n" + //
                                "\r\n" + //
                                "Caching\r\n" + //
                                "\r\n" + //
                                "Redirection and forwarding\r\n" + //
                                "\r\n" + //
                                "Security (encryption and authentication)\r\n" + //
                                "\r\n" + //
                                "These are all critical factors when building resilient services. However, that is not all. The web is built out of lots of tiny specs. This architecture lets it easily evolve without getting bogged down in \"standards wars\".\r\n" + //
                                "\r\n" + //
                                "Developers can draw upon third-party toolkits that implement these diverse specs and instantly have both client and server technology at their fingertips.\r\n" + //
                                "\r\n" + //
                                "By building on top of HTTP, REST APIs provide the means to build:\r\n" + //
                                "\r\n" + //
                                "Backwards compatible APIs\r\n" + //
                                "\r\n" + //
                                "Evolvable APIs\r\n" + //
                                "\r\n" + //
                                "Scaleable services\r\n" + //
                                "\r\n" + //
                                "Securable services\r\n" + //
                                "\r\n" + //
                                "A spectrum of stateless to stateful services\r\n" + //
                                "\r\n" + //
                                "Note that REST, however ubiquitous, is not a standard per se but an approach, a style, a set of constraints on your architecture that can help you build web-scale systems. This tutorial uses the Spring portfolio to build a RESTful service while takin advantage of the stackless features of REST.");
            post01.setAccount(account01);
            postService.save(post01);

            // Create second post and associate with account02
            Post post02 = new Post();
            post02.setTitle("Post02 title");
            post02.setBody("Post02 Body........");
            post02.setAccount(account02);
            postService.save(post02);


            Post post03 = new Post();
            post03.setTitle("Post03 title");
            post03.setBody("Post03 Body........");
            post03.setAccount(account03);
            postService.save(post03);
        }
		
	}
}


