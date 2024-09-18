package com.demoboletto.domain;

import com.demoboletto.type.EFrame;
import com.demoboletto.type.ESticker;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "collect")
public class Collect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collect_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "frame_type")
    @Enumerated(EnumType.STRING)
    private EFrame frameType;

    @Column(name = "sticker_type")
    @Enumerated(EnumType.STRING)
    private ESticker stickerType;

    @Column(name = "collected_at", nullable = false)
    private LocalDateTime collectedAt;

    @Builder
    public Collect(User user, EFrame frameType, ESticker stickerType) {
        this.user = user;
        this.frameType = frameType;
        this.stickerType = stickerType;
        this.collectedAt = LocalDateTime.now();
    }
}
