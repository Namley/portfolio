package com.namley.portfolio.service;

import com.namley.portfolio.model.Post;
import com.namley.portfolio.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public Post saveTweet(Post tweet) {
        return postRepository.save(tweet);
    }
    public List<Post> getAllTweets() {
        return postRepository.findAll();
    }
}
