package com.demoboletto.security.handler.login;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {

        // 로그인 실패 응답 생성 (iOS 클라이언트에서 처리 가능하도록 JSON 형태로 응답)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String errorMessage = "소셜 로그인에 실패했습니다. 에러 메시지 : " + exception.getMessage();
        log.info(errorMessage);

        // JSON 형식으로 응답
        response.getWriter().write("{ \"error\": \"OAuth2 authentication failed\", \"message\": \"" + errorMessage + "\" }");

        log.info("소셜 로그인 실패");
    }
}
