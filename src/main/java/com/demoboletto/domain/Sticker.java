package com.demoboletto.domain;

import com.demoboletto.dto.request.CreateStickerDto;
import com.demoboletto.type.ESticker;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "sticker")
@NoArgsConstructor
@Getter
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sticker_id")
    private Long stickerId;

    @Column(name = "field")
    @Enumerated(EnumType.STRING)
    private ESticker field;

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
    @Builder
    public Sticker(ESticker field, float locX, float locY, Travel travel, int rotation, float scale) {
        this.field = field;
        this.locX = locX;
        this.locY = locY;
        this.travel = travel;
        this.rotation = rotation;
        this.scale = scale;
    }

    public static Sticker create(CreateStickerDto stickerDto, Travel travel) {
        return Sticker.builder()
                .field(stickerDto.field())
                .locX(stickerDto.locX())
                .locY(stickerDto.locY())
                .rotation(stickerDto.rotation())
                .scale(stickerDto.scale())
                .travel(travel)
                .build();
    }
}
