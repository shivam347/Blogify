package com.example.SpringStarter.Models;

/**
 * Represents a user account in the system.
 * Each account can have multiple posts.
 */
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {

    /**
     * Primary key for Account entity.
     */
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE) 
    private Long id;

    /**
     * Username of the account.
     */
    private String username;

    /**
     * Email address of the account.
     */
    private String email;

    /**
     * Password for the account.
     */
    private String password;


    private String role;

    /**
     * List of posts associated with this account.
     */
    @OneToMany(mappedBy = "account")
    private List<Post> posts;
    
}
