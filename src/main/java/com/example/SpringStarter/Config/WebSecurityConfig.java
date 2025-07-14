package com.example.SpringStarter.Config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// Import Spring Security configuration annotations
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * WebSecurityConfig class is used to configure Spring Security for the
 * application.
 * 
 * @EnableWebSecurity enables web security in the project.
 * @EnableMethodSecurity enables method-level security annotations.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

    // List of URLs that are whitelisted (accessible without authentication)
    private static final String[] WHITELIST_URLS = {
            "/home",
            "/register",
            "/login",
            "/db-console/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/favicon.ico",
            "/fonts/**"
    };


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(WHITELIST_URLS).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout?success=true")
                .permitAll()
            )
            .httpBasic()
            .and()
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    // Security configuration logic will be
}
