package com.namley.portfolio.controller;

import com.namley.portfolio.model.Message;
import com.namley.portfolio.model.Post;
import com.namley.portfolio.service.MessageService;
import com.namley.portfolio.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final PostService postService;
    private final MessageService messageService;
    @GetMapping("")
    public String index(Model model)
    {
        List<Post> allPosts = postService.getAllTweets();
        allPosts.sort((Comparator.comparing(Post::getCreatedAt).reversed()));
        List<Message> allMessages = messageService.getAllMessages();
        allMessages.sort((Comparator.comparing(Message::getCreatedAt).reversed()));
        model.addAttribute("posts",allPosts);
        model.addAttribute("post",new Post());
        model.addAttribute("userMessage", new Message());
        model.addAttribute("userMessages", allMessages);
        return "index";
    }
}
