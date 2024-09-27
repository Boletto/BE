package com.demoboletto.domain;

import com.demoboletto.dto.request.CreateSpeechDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Speech")
@NoArgsConstructor
@Getter
public class Speech {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "speech_id")
    private Long speechId;

    @Column(name = "text")
    private String text;

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
    public Speech(String text, float locX, float locY, Travel travel, int rotation, float scale) {
        this.text = text;
        this.locX = locX;
        this.locY = locY;
        this.travel = travel;
        this.rotation = rotation;
        this.scale = scale;
    }
    public static Speech create(CreateSpeechDto speechDto, Travel travel) {
        return Speech.builder()
                .text(speechDto.text())
                .locX(speechDto.locX())
                .locY(speechDto.locY())
                .rotation(speechDto.rotation())
                .scale(speechDto.scale())
                .travel(travel)
                .build();
    }
}
