package com.namley.portfolio.controller;

import com.namley.portfolio.model.Post;
import com.namley.portfolio.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLOutput;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping("/posts")
    public Post createTweet(@RequestBody Post tweet) {
        return postService.saveTweet(tweet);
    }
}
