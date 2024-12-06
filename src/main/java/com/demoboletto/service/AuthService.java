package com.demoboletto.service;

import com.demoboletto.domain.User;
import com.demoboletto.dto.oauth.common.OAuthUserInformation;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final EntityManager entityManager;

    public User signUp(OAuthUserInformation userInfo) {
        User user = User.signUp(userInfo);
        entityManager.persist(user);
        return user;
    }
}
