package com.namley.portfolio.config;

import com.namley.portfolio.service.OAuth2UserService;
import com.namley.portfolio.service.OIDCUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    private final OIDCUserService oidcUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .headers(header -> {
                    header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                })
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/", "/login").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/css/*").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/js/*").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/img/*").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/proxy-image/*").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/images/*").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/plane/*").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/posts").hasRole("ADMIN");
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/messages").hasRole("USER");
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/h2-console/*").hasRole("ADMIN");
                    authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
                })
                .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .oauth2Login(oauth2 -> oauth2.userInfoEndpoint(
                                        userInfoEndpointConfig -> userInfoEndpointConfig.userService(oAuth2UserService).oidcUserService(oidcUserService)
                                )
                        .loginPage("/login")
                                .successHandler((request, response, authentication) -> response.sendRedirect("/"))
                )
                .build();
    }

}
