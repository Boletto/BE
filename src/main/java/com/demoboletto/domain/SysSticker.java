package com.demoboletto.domain;

import com.demoboletto.domain.common.BaseTimeEntity;
import com.demoboletto.type.ESticker;
import com.demoboletto.type.EStickerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sys_sticker")
@NoArgsConstructor
@Getter
public class SysSticker extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sticker_name")
    private String stickerName;

    @Column(name = "sticker_type")
    private EStickerType stickerType;

    @Column(name = "default_provided")
    private boolean defaultProvided;

    @Column(name = "sticker_url")
    private String stickerUrl;

    @Column(name = "description")
    private String description;


}
