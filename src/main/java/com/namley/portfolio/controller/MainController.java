package com.namley.portfolio.controller;

import com.namley.portfolio.model.Post;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("")
    public String index(Post post, Model model)
    {
        model.addAttribute("post",post);
        return "index";
    }
}
