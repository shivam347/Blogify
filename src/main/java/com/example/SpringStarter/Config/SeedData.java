package com.example.SpringStarter.Config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.SpringStarter.Models.Account;
import com.example.SpringStarter.Models.Post;
import com.example.SpringStarter.Service.AccountService;
import com.example.SpringStarter.Service.PostService;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;  

	@Override
	public void run(String... args) throws Exception {
  
        Account account01 = new Account();
        Account account02 = new Account();
        account01.setUsername("vidhi");
        account01.setPassword("vidhi123");
        account01.setEmail("vidhi123@gmail.com");

        account02.setUsername("shivam");
        account02.setPassword("shivm123");
        account02.setEmail("shivam123@gmail.com");

        accountService.save(account01);
        accountService.save(account02);

		List<Post> posts = postService.getAll();

        if(posts.size() == 0){
            Post post01 = new Post();

            post01.setTitle("post01 title");
            post01.setBody("post01 Body............");
            post01.setAccount(account01);

            postService.save(post01);


            Post post02 = new Post();

            post02.setTitle("Post02 title");
            post02.setBody("Post02 Body........");
            post02.setAccount(account02);

            postService.save(post02);

        }
		
	}
}


