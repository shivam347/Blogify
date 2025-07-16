package com.example.SpringStarter.Config;

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
        account01.setPassword("vidhi123"); // Password will be encoded in AccountService
        account01.setEmail("vidhi123@gmail.com");
        account01.setRole(Roles.EDITOR.getRole());

        // Set properties for the second account
        account02.setUsername("shivam");
        account02.setPassword("shivm123"); // Password will be encoded in AccountService
        account02.setEmail("shivam123@gmail.com");
        account02.setRole(Roles.ADMIN.getRole());


        // Set properties for the third account
        account03.setUsername("vaibhav");
        account03.setPassword("vaibhav123"); // Password will be encoded in AccountService
        account03.setEmail("vaibhav123@gmail.com");

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
            post01.setBody("post01 Body............");
            post01.setAccount(account01);
            postService.save(post01);

            // Create second post and associate with account02
            Post post02 = new Post();
            post02.setTitle("Post02 title");
            post02.setBody("Post02 Body........");
            post02.setAccount(account02);
            postService.save(post02);
        }
		
	}
}


