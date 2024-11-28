package com.demoboletto.security.config;

import com.demoboletto.constants.Constants;
import com.demoboletto.security.filter.GlobalLoggerFilter;
import com.demoboletto.security.filter.JwtAuthenticationFilter;
import com.demoboletto.security.filter.JwtExceptionFilter;
import com.demoboletto.security.handler.exception.CustomAccessDeniedHandler;
import com.demoboletto.security.handler.exception.CustomAuthenticationEntryPointHandler;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomLogoutProcessHandler customLogoutProcessHandler;
    private final CustomLogoutResultHandler customLogoutResultHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;
    private final JwtAuthenticationManager jwtAuthenticationManager;
    private final JwtUtil jwtUtil;

    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // 상태 비저장 설정

                .authorizeHttpRequests(registry ->
                        registry
                                .requestMatchers(Constants.NO_NEED_AUTH_URLS.toArray(String[]::new)).permitAll()
                                .anyRequest().authenticated())

                // 소셜 로그인 설정
//                .oauth2Login(oauth2 -> oauth2
//                        .successHandler(oAuth2LoginSuccessHandler)  // 성공 시 핸들러
//                        .failureHandler(oAuth2LoginFailureHandler)  // 실패 시 핸들러
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userService(customOAuth2UserService))  // 사용자 정보 처리
//                )

                // 로그아웃 처리
                .logout(configurer ->
                        configurer
                                .logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(customLogoutProcessHandler)
                                .logoutSuccessHandler(customLogoutResultHandler))

                .exceptionHandling(configurer ->
                        configurer
                                .accessDeniedHandler(customAccessDeniedHandler)
                                .authenticationEntryPoint(customAuthenticationEntryPointHandler))

                // JWT 필터 추가
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


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // 허용할 Origin 설정 -> 변경 필요
        configuration.addAllowedMethod("*"); // 허용할 HTTP Method 설정
        configuration.addAllowedHeader("*"); // 허용할 HTTP Header 설정
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Authorization-refresh");
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용 -> 변경 필요

        return source;
    }


}
