package com.namley.portfolio.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final OAuth2User oAuth2User;
    private String email;
    private String profilePictureUrl;

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (getEmail() != null && getEmail().equals("lehoainam0106@gmail.com")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        authorities.addAll(oAuth2User.getAuthorities());
        return authorities;
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("login");
    }
}
