package com.demoboletto.domain;

import com.demoboletto.domain.common.BaseTimeEntity;
import com.demoboletto.type.EStickerType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sys_sticker")
@NoArgsConstructor
@Getter
public class SysSticker extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sticker_id")
    private Long stickerId;

    @Column(name = "sticker_name")
    private String stickerName;

    @Column(name = "sticker_code", unique = true)
    private String stickerCode;

    @Column(name = "sticker_type")
    @Enumerated(EnumType.STRING)
    private EStickerType stickerType;

    @Column(name = "default_provided")
    private boolean defaultProvided;

    @Column(name = "sticker_url")
    private String stickerUrl;

    @Column(name = "description")
    private String description;

    @Builder
    SysSticker(String stickerCode, String stickerName, EStickerType stickerType, boolean defaultProvided, String stickerUrl, String description) {
        this.stickerCode = stickerCode;
        this.stickerName = stickerName;
        this.stickerType = stickerType;
        this.defaultProvided = defaultProvided;
        this.stickerUrl = stickerUrl;
        this.description = description;
    }

}
