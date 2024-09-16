package com.namley.portfolio.controller;

import com.namley.portfolio.model.Post;
import com.namley.portfolio.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private static String IMAGES_FOLDER = "c:/Users/lehoa/Documents/uploaded-images/";
    @PostMapping("/posts")
    public ResponseEntity<String> createTweet(@RequestParam("imageUploader") MultipartFile file, @ModelAttribute Post post, Model model, RedirectAttributes redirectAttributes) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setLocation(URI.create("/"));
        if (!file.isEmpty()) {
            try {
                // Get the file name and build the path where it will be stored
                byte[] bytes = file.getBytes();
                Path path = Paths.get(IMAGES_FOLDER + file.getOriginalFilename());

                post.setImagePath("/images/" + file.getOriginalFilename());
                // Save the file to the disk
                Files.write(path, bytes);

                // Add success message and file URL to the model
                redirectAttributes.addFlashAttribute("success", true);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                redirectAttributes.addFlashAttribute("message", "File upload failed.");
            }
        }
        post.setCreatedAt(Instant.now());
        if (post.getContent().isEmpty() && file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Tweet cannot be empty.");
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
        post.setContent(parseTweetHtml(post.getContent()));

        postService.saveTweet(post);
        model.addAttribute("posts", postService.getAllTweets());

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
    private String parseTweetHtml(String html) {
        // Step 1: Remove <br> tags entirely
        html = html.replaceAll("(?i)<br\\s*/?>", "");  // Removes both <br> and <br/>

        // Step 2: Remove opening <div> tags
        html = html.replaceAll("(?i)<div[^>]*>", "");  // Removes opening <div> tags

        // Step 3: Replace closing </div> tags with newline (\n)
        html = html.replaceAll("(?i)</div\\s*>", "\n");  // Replaces closing </div> with newline

        // Step 4: Trim any extra spaces or newlines at the start/end
        return html.trim();
    }
}
