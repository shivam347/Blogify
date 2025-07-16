package com.example.SpringStarter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringStarter.Models.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    // Additional query methods can be defined here if needed
}