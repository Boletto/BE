package com.demoboletto.config;

import com.demoboletto.constants.Constants;
import com.demoboletto.intercepter.pre.UserIdArgumentResolver;
import com.demoboletto.intercepter.pre.UserIdInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMVCConfig implements WebMvcConfigurer {
    private final UserIdArgumentResolver userIdArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(this.userIdArgumentResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new UserIdInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(Constants.NO_NEED_AUTH_URLS);
    }
}

