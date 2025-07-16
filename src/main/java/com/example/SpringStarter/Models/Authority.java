package com.example.SpringStarter.Models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Authority {


    private Long id;

    private String authorityName;

    public Authority(Long id, String authorityName){
        this.id = id;
        this.authorityName = authorityName;
    }
    

    @ManyToMany
    @JoinTable(
        name = "account_authority",
        joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id")
    )
    private Set<Authority> authorities = new HashSet<>();
}
