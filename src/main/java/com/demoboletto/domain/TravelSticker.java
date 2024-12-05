package com.demoboletto.domain;

import com.demoboletto.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "travel_sticker")
@NoArgsConstructor
@Getter
public class TravelSticker extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_sticker_id")
    private Long travelStickerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sticker_id")
    private SysSticker sysSticker;

    @Column(name = "loc_x")
    private float locX;

    @Column(name = "loc_y")
    private float locY;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;

    @Column(name = "rotation")
    private int rotation;

    @Column(name = "scale")
    private float scale;

    @Column(name = "content")
    private String content;

    @Builder
    public TravelSticker(SysSticker sysSticker, float locX, float locY, Travel travel, int rotation, float scale, String content) {
        this.sysSticker = sysSticker;
        this.locX = locX;
        this.locY = locY;
        this.travel = travel;
        this.rotation = rotation;
        this.scale = scale;
        this.content = content;
    }
}


