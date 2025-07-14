package com.example.SpringStarter.Config;


import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

    private static final String[] WHITELIST_URLS = {
      
        "/home",
        "/register",
        "/login",
        "/db-console"
        
    };
	
}
