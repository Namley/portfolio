package com.namley.portfolio.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {
    @GetMapping("/isAdmin")
    public Map<String, Object> isAdmin(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("isAdmin", principal.getAttribute("name"));
    }
}
