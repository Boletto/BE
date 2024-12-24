package com.demoboletto.domain;

import com.demoboletto.domain.common.BaseTimeEntity;
import com.demoboletto.dto.oauth.common.OAuthUserInformation;
import com.demoboletto.type.EProvider;
import com.demoboletto.type.ERole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "member")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @SequenceGenerator(name = "member_seq", sequenceName = "member_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
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

    @Column(name = "user_profile")
    private String userProfile;

    @Column(name = "device_token")
    private String deviceToken;

    @Column(name = "apple_refresh_token")
    private String appleRefreshToken;

    @Builder
    public User(String serialId, String password, String email, String name, String nickname, EProvider provider, ERole role, String userProfile) {
        this.serialId = serialId;
        this.password = password;
        this.provider = provider;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.isFrame = true;
        this.isFriendApply = true;
        this.isLocation = true;
        this.userProfile = userProfile != null ? userProfile : "default";
    }

    // 소셜 로그인
    public static User signUp(OAuthUserInformation info) {
        return User.builder()
                .email(info.getEmail())
                .name(info.getName())
                .provider(info.getProvider())
                .serialId(info.getSerialId())
                .userProfile(info.getProfileImgUrl())
                .role(ERole.USER)
                .build();
    }

    public void register(String nickname) {
        this.nickname = nickname;
        this.role = ERole.USER;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateSignOutUser() {
        this.role = ERole.DELETED;
    }

    public void updateDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void invalidateRefreshToken() {
        this.refreshToken = null;
    }

    public void invalidateDeviceToken() {
        this.deviceToken = null;
    }

    public void updateAppleRefreshToken(String refreshToken) {
        this.appleRefreshToken = refreshToken;
    }
}
