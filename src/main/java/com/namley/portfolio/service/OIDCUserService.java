package com.namley.portfolio.service;

import com.namley.portfolio.model.CustomOIDCUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OIDCUserService extends OidcUserService {
    private final String proxyImageEndpoint = "/proxy-image?imageUrl=";

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        final OidcUser oidcUser = super.loadUser(userRequest);
        CustomOIDCUser newUser = new CustomOIDCUser(oidcUser);

        if (oidcUser.getAttributes().get("email") != null) {
            newUser.setEmail(oidcUser.getAttributes().get("email").toString());
        }
        if (oidcUser.getAttributes().get("picture") != null) {
            newUser.setProfilePictureUrl(proxyImageEndpoint + oidcUser.getAttributes().get("picture").toString());
        } else if (oidcUser.getAttributes().get("avatar_url") != null) {
            newUser.setProfilePictureUrl(proxyImageEndpoint + oidcUser.getAttributes().get("avatar_url").toString());
        }

        return newUser;
    }
}
