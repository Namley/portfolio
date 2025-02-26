package com.namley.portfolio.controller;

import com.namley.portfolio.model.CustomOAuth2User;
import com.namley.portfolio.model.CustomOIDCUser;
import com.namley.portfolio.model.Message;
import com.namley.portfolio.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;


@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageRepository messageRepository;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/messages")
    public String postMessage(@ModelAttribute Message message, RedirectAttributes redirectAttributes, @AuthenticationPrincipal CustomOAuth2User principal, @AuthenticationPrincipal CustomOIDCUser oidcPrincipal) {
        if (message.getMessageContent().isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Message cannot be empty.");
            return "redirect:/";
        }

        if (principal == null) {
            message.setUsername(oidcPrincipal.getName());
            message.setProfileImageUrl(oidcPrincipal.getProfilePictureUrl());
        } else {
            message.setUsername(principal.getName());
            message.setProfileImageUrl(principal.getProfilePictureUrl());
        }
        message.setCreatedAt(Instant.now());
        messageRepository.save(message);

        return "redirect:/";
    }
}

