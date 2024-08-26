package com.namley.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .headers(header -> {
                    header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                })
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/css/*").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/img/*").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/plane/*").permitAll();
                    authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
                })
                .oauth2Login(withDefaults())
                .build();
    }
}
