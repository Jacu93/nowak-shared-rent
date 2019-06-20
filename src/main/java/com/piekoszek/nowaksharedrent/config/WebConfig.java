package com.piekoszek.nowaksharedrent.config;

import com.piekoszek.nowaksharedrent.jwt.HeaderJwtArgumentResolver;
import com.piekoszek.nowaksharedrent.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@AllArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private JwtService jwtService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HeaderJwtArgumentResolver(jwtService));
    }
}