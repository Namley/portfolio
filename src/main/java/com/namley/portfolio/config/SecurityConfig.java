package com.namley.portfolio.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .headers(header -> {
                    header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                })
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/","/login").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/css/*").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/img/*").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/plane/*").permitAll();
                    authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> response.sendRedirect("/user"))
                )
                .build();
    }
}
