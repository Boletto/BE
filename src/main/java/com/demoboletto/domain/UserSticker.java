package com.demoboletto.domain;

import com.demoboletto.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "user_sticker", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_sticker", columnNames = {"user_id", "sticker_id"})
})
public class UserSticker extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_sticker_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sticker_id", nullable = false)
    private SysSticker sticker;

    @Builder
    public UserSticker(User user, SysSticker sticker) {
        this.user = user;
        this.sticker = sticker;
    }
}
