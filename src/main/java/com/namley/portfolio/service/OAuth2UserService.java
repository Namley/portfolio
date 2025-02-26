package com.namley.portfolio.service;

import com.namley.portfolio.model.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final String proxyImageEndpoint = "/proxy-image?imageUrl=";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = super.loadUser(userRequest);
        CustomOAuth2User newUser = new CustomOAuth2User(oAuth2User);

        if (oAuth2User.getAttributes().get("email") != null) {
            newUser.setEmail(oAuth2User.getAttributes().get("email").toString());
        }
        if (oAuth2User.getAttributes().get("picture") != null) {
            newUser.setProfilePictureUrl(proxyImageEndpoint + oAuth2User.getAttributes().get("picture").toString());
        } else if (oAuth2User.getAttributes().get("avatar_url") != null) {
            newUser.setProfilePictureUrl(proxyImageEndpoint + oAuth2User.getAttributes().get("avatar_url").toString());
        }

        return newUser;
    }

}
