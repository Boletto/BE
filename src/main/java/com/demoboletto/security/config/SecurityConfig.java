package com.demoboletto.security.config;

import com.demoboletto.constants.Constants;
import com.demoboletto.security.filter.GlobalLoggerFilter;
import com.demoboletto.security.filter.JwtAuthenticationFilter;
import com.demoboletto.security.filter.JwtExceptionFilter;
import com.demoboletto.security.handler.exception.CustomAccessDeniedHandler;
import com.demoboletto.security.handler.exception.CustomAuthenticationEntryPointHandler;
import com.demoboletto.security.handler.login.DefaultFailureHandler;
import com.demoboletto.security.handler.login.DefaultSuccessHandler;
import com.demoboletto.security.handler.logout.CustomLogoutProcessHandler;
import com.demoboletto.security.handler.logout.CustomLogoutResultHandler;
import com.demoboletto.security.provider.JwtAuthenticationManager;
import com.demoboletto.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final DefaultSuccessHandler defaultSuccessHandler;
    private final DefaultFailureHandler defaultFailureHandler;
    private final CustomLogoutProcessHandler customSignOutProcessHandler;
    private final CustomLogoutResultHandler customSignOutResultHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;
    private final JwtAuthenticationManager jwtAuthenticationManager;
    private final JwtUtil jwtUtil;

    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안하고 상태가 없는 방식으로 인증 = JWT 사용
                )

                .authorizeHttpRequests(registry ->
                        registry
                                .requestMatchers(Constants.NO_NEED_AUTH_URLS.toArray(String[]::new)).permitAll()
                                .anyRequest().authenticated()
                )

                .formLogin(configurer ->
                        configurer
                                .loginPage("/login")
                                .loginProcessingUrl("/api/v1/auth/login")
                                .usernameParameter("serial_id")
                                .passwordParameter("password")
                                .successHandler(defaultSuccessHandler)
                                .failureHandler(defaultFailureHandler)
                )
                .logout(configurer ->
                        configurer
                                .logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(customSignOutProcessHandler)
                                .logoutSuccessHandler(customSignOutResultHandler)
                )
                .exceptionHandling(configurer ->
                        configurer
                                .accessDeniedHandler(customAccessDeniedHandler)
                                .authenticationEntryPoint(customAuthenticationEntryPointHandler)
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil, jwtAuthenticationManager),
                        LogoutFilter.class)
                .addFilterBefore(
                        new JwtExceptionFilter(),
                        JwtAuthenticationFilter.class)
                .addFilterBefore(
                        new GlobalLoggerFilter(),
                        JwtExceptionFilter.class)

                .getOrBuild();
    }
}
