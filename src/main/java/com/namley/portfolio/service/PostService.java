package com.namley.portfolio.service;

import com.namley.portfolio.model.Post;
import com.namley.portfolio.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post saveTweet(Post tweet) {
        return postRepository.save(tweet);
    }
}
