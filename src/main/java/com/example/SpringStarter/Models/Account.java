package com.example.SpringStarter.Models;

import java.util.HashSet;
/**
 * Represents a user account in the system.
 * Each account can have multiple posts.
 */
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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


    /**
     * Set of roles assigned to the account.
     */
    private String role;

    /**
     * List of posts associated with this account.
     */
    @OneToMany(mappedBy = "account")
    private List<Post> posts;



    @ManyToMany
    @JoinTable(name = "account_authority",
                joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    
    
    
               
     private Set<Authority> authorities = new HashSet<>();

    /**X
     * Returns the string representation of the account.
     * @return String representation of the account.
     */
    


    
}
