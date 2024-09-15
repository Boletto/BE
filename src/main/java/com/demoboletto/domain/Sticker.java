package com.demoboletto.domain;

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

    @Builder
    public Sticker(ESticker field, float locX, float locY, Travel travel) {
        this.field = field;
        this.locX = locX;
        this.locY = locY;
        this.travel = travel;
    }
}
