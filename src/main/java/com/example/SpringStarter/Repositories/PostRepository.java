package com.example.SpringStarter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.SpringStarter.Models.Post;

// the @Repository annotation is used to indicate that a class is a Data Access Object (DAO) or 
// repository which interacts with the database.
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
}
