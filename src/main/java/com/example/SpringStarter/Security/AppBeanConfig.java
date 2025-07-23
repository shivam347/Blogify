package com.example.SpringStarter.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class AppBeanConfig {

    /**
     * Bean for password encoding using BCrypt.
     * This is used to hash passwords before storing them in the database
     * and to verify passwords during authentication.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
