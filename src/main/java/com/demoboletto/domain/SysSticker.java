package com.demoboletto.domain;

import com.demoboletto.domain.common.BaseTimeEntity;
import com.demoboletto.type.EStickerType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDate;

@Entity
@Table(name = "sys_sticker")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    @Column(name = "default_provided", nullable = false)
    private boolean defaultProvided;

    @Column(name = "sticker_url")
    private String stickerUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "is_event", nullable = false)
    private Boolean isEvent;

    @Column(name = "event_start_date")
    private LocalDate eventStartDate;

    @Column(name = "event_end_date")
    private LocalDate eventEndDate;

    @Builder
    public SysSticker(Long stickerId, String stickerName, String stickerCode, EStickerType stickerType, boolean defaultProvided, String stickerUrl, String description, Boolean isEvent, LocalDate eventStartDate, LocalDate eventEndDate) {
        this.stickerId = stickerId;
        this.stickerName = stickerName;
        this.stickerCode = stickerCode;
        this.stickerType = stickerType;
        this.defaultProvided = defaultProvided;
        this.stickerUrl = stickerUrl;
        this.description = description;
        this.isEvent = isEvent;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
    }

    public boolean isEventExpired() {
        LocalDate now = LocalDate.now();
        return isEvent && eventEndDate.isBefore(now);
    }
}
