package com.namley.portfolio.controller;

import com.namley.portfolio.model.Post;
import com.namley.portfolio.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Value("${app.upload.dir:${user.dir}/src/main/resources/static/uploaded-images}")
    private String uploadDir;

    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/gif"
    );

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/posts")
    public String createTweet(@RequestParam("imageUploader") MultipartFile file,
                              @ModelAttribute Post post,
                              RedirectAttributes redirectAttributes) {

        if (post.getContent().isEmpty() && file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Tweet cannot be empty.");
            return "redirect:/";
        }

        if (!file.isEmpty()) {
            if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
                redirectAttributes.addFlashAttribute("message", "Only JPEG, PNG and GIF images are allowed.");
                return "redirect:/";
            }

            if (file.getSize() > MAX_FILE_SIZE) {
                redirectAttributes.addFlashAttribute("message", "File size must be less than 5MB.");
                return "redirect:/";
            }

            try {
                // Create upload directory if it doesn't exist
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = file.getOriginalFilename();
                assert originalFilename != null;
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String filename = Instant.now().toEpochMilli() + fileExtension;
                Path filePath = uploadPath.resolve(filename);

                // Save file using NIO
                Files.copy(file.getInputStream(), filePath);

                post.setImagePath("/images/" + filename);

            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("message",
                        "Failed to upload image: " + e.getMessage());
                return "redirect:/";
            }
        }

        post.setCreatedAt(Instant.now());
        post.setContent(parseTweetHtml(post.getContent()));
        postService.saveTweet(post);

        return "redirect:/";
    }

    private String parseTweetHtml(String html) {
        if (html == null) return "";
        html = html.replaceAll("(?i)<br\\s*/?>", "");
        html = html.replaceAll("(?i)<div[^>]*>", "");
        html = html.replaceAll("(?i)</div\\s*>", "\n");
        return html.trim();
    }
}