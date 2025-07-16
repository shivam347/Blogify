package com.example.SpringStarter.Models;


import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Authority {


    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE)
    private Long id;

    private String authorityName;

    public Authority(String authorityName) {
        this.authorityName = authorityName;
    }       


    @ManyToMany(mappedBy = "authorities")
    private Set<Account> accounts;
    

   
   
}
