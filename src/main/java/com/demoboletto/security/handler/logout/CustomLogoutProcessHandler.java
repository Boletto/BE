package com.demoboletto.security.handler.logout;

import com.demoboletto.domain.User;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.security.info.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CustomLogoutProcessHandler implements LogoutHandler {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {
        if (authentication == null) {
            return;
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        //TODO: Refactor this line
        //TODO: 디바이스 토큰 제거
        User user = userRepository.findUserById(userPrincipal.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.updateDeviceToken(null);
        user.invalidateRefreshToken();
    }
}
