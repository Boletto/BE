package com.demoboletto.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "fourCut")
public class FourCut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fourCut_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id", nullable = true)
    private Travel travel;

    @Column(name = "collect_id")
    private Long collectId;

    @Column(name = "picture_idx")
    private int pictureIdx;

    @Builder
    public FourCut(Travel travel, Long collectId, int pictureIdx) {
        this.travel = travel;
        this.collectId = collectId;
        this.pictureIdx = pictureIdx;
    }
    public static FourCut create(int idx, Travel travel, Long collectId) {
        return FourCut.builder()
                .travel(travel)
                .pictureIdx(idx)
                .collectId(collectId)
                .build();
    }

}
