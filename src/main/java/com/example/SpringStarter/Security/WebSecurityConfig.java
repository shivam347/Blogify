package com.example.SpringStarter.Security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// Import Spring Security configuration annotations
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.SpringStarter.Service.AccountService;
import com.example.SpringStarter.Util.Constants.Privileges;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private AccountService accountService;

   

    // List of URLs that are whitelisted (accessible without authentication)
    private static final String[] WHITELIST_URLS = {
            "/home",           // Home page
            "/register",       // Registration page
            "/login",          // Login page
            "/db-console/**",  // H2 database console
            "/css/**",         // Static CSS resources
            "/js/**",          // Static JS resources
            "/images/**",      // Static image resources
            "/favicon.ico",    // Favicon
            "/fonts/**"        // Static font resources
    };

    

    /**
     * Bean for password encoding using BCrypt.
     * This is used to hash passwords before storing them in the database
     * and to verify passwords during authentication.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain for HTTP requests.
     * Sets up URL authorization, login/logout handling, CSRF, and frame options.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // Allow whitelisted URLs without authentication, require authentication for others
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(WHITELIST_URLS).permitAll()
                .requestMatchers("/profile/**").authenticated()
                .requestMatchers("/admin/**").hasRole("ADMIN") // Only allow ADMIN role for /admin/** URLs
                .requestMatchers("/editor/**").hasAnyRole("ADMIN", "EDITOR") // Allow ADMIN and EDITOR roles for /editor/** URLs
                .requestMatchers("/admin/**").hasAuthority(Privileges.ACCES_ADMIN_PANEL.getAuthorityName())// Only allow ADMIN role for /admin/** URLs
                .anyRequest().authenticated()
            )
            // Configure form-based login
            .formLogin(form -> form
                .loginPage("/login")                      // Custom login page
                .loginProcessingUrl("/login")              // URL to submit login form
                .usernameParameter("email")                // Username parameter name
                .passwordParameter("password")             // Password parameter name
                .defaultSuccessUrl("/home", true)          // Redirect after successful login
                .failureUrl("/login?error=true")           // Redirect after failed login
                .permitAll()                               // Allow all to access login page
            )
            // Configure logout handling
            .logout(logout -> logout
                .logoutUrl("/logout")                      // URL to trigger logout
                .logoutSuccessUrl("/home")  // Redirect after successful logout
                .permitAll()                               // Allow all to access logout
            )

            .rememberMe(remember -> remember
                .key("uniqueAndSecret")
                .tokenValiditySeconds(7 * 24 * 60 * 60) // 7 days
                .userDetailsService(accountService)
                .rememberMeParameter("remember-me")  // optional default is remember-me
                .rememberMeCookieName("remember-me")  // optional 
        
            )
            
            // Enable HTTP Basic authentication (for APIs, etc.)
            .httpBasic(withDefaults())
            // Disable CSRF protection (useful for H2 console, but not recommended for production)
            .csrf(csrf -> csrf.disable())
            // Allow frames for H2 console (disable frame options header)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        // Build and return the security filter chain
        return http.build();
    }

    // Additional security configuration logic can be added here if needed
}
