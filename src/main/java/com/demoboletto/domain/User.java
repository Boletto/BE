package com.demoboletto.domain;

import com.demoboletto.type.EProfile;
import com.demoboletto.type.EProvider;
import com.demoboletto.type.ERole;
import com.demoboletto.dto.request.AuthSignUpDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", nullable = false)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "serial_id", nullable = false, unique = true)
    private String serialId;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private EProvider provider;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "is_frame")
    private boolean isFrame;

    @Column(name = "is_friendapply")
    private boolean isFriendApply;

    @Column(name = "is_location")
    private boolean isLocation;

    @Column(name = "is_login")
    private boolean isLogin;

    @Column(name = "user_profile")
    private String userProfile;

//    @Column(name="device_token")
//    private String deviceToken;

    @Builder
    public User(String serialId, String password, String email, String name, String nickname, EProvider provider, ERole role, String userProfile) {
        this.serialId = serialId;
        this.password = password;
        this.provider = provider;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.email =email;
        this.name = name;
        this.nickname = nickname;
//        this.deviceToken = deviceToken;
        this.isFrame=true;
        this.isFriendApply=true;
        this.isLocation=true;
        this.isLogin=true;
        this.userProfile = userProfile != null ? userProfile : "default";
    }

    public void register(String nickname) {
        this.nickname = nickname;
        this.role = ERole.USER;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    // 소셜 로그인
    public static User signUp(String serialId, EProvider provider, String nickname) {
        return User.builder()
                .serialId(serialId)
                .provider(provider)
                .password(null)
                .nickname(nickname)
                .name(null)
//                .deviceToken(deviceToken)
                .role(ERole.USER)
                .build();
    }

    public void updateProfile(String nickname, String name, String profileUrl) {
        this.nickname = nickname;
        this.name = name;
        this.userProfile= profileUrl;
    }

    public void updateSignOutUser(){
        this.role = ERole.DELETED;
        this.isLogin=false;
    }

//    public void updateDeviceToken(String deviceToken) {
//        this.deviceToken = deviceToken;
//    }
}
