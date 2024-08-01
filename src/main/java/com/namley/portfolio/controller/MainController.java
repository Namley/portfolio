package com.namley.portfolio.controller;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("")
    public String index(@RequestParam(name = "name",required = false,defaultValue = "World") String name, Model model)
    {
        model.addAttribute("name",name);
        return "index";
    }

    @PostMapping("/posts")
    public String createPost(@PostMapping)
    {
    }
}
