package com.example.SpringStarter.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.SpringStarter.Models.Post;
import com.example.SpringStarter.Repositories.PostRepository;

@Service
public class PostService {
 
    @Autowired
    private PostRepository postRepository;


    // to get the post by id , optional is used like if not found return nothing
    public Optional<Post> getById(Long id){
        return postRepository.findById(id);
    }

    // to get all the posts
    public List<Post> findAll(){
        return postRepository.findAll();
    }


    public Page<Post> findAll(int offset, int pageSize, String field){
        PageRequest pageRequest = PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC, field));
        return postRepository.findAll(pageRequest);
    }

    // to delete the post
    public void delete(Post post){
        postRepository.delete(post);
    }

    // to save the post
    public Post save(Post post){
        if(post.getId() == null){
            post.setCreatedAt(LocalDateTime.now());
        }

        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }
    
}
