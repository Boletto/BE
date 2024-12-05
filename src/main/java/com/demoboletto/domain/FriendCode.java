package com.demoboletto.domain;

import com.demoboletto.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@Table(name = "friend_code")
public class FriendCode extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_code_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "friend_code")
    private String friendCode;

    @Column(name = "is_used")
    private boolean isUsed;

    //TODO: 만료일에 record 삭제하는 batch job 추가
    @Column(name = "expired_at")
    private LocalDate expiredAt;

    @Builder
    public FriendCode(User user, String friendCode, boolean isUsed, LocalDate expiredAt) {
        this.user = user;
        this.friendCode = friendCode;
        this.isUsed = isUsed;
        this.expiredAt = expiredAt;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}
