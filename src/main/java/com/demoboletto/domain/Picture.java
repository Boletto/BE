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
@Table(name = "picture")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id", nullable = true)
    private Travel travel;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "picture_idx")
    private int pictureIdx;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_fourCut")
    private boolean isFourCut;

    @Column(name = "sub_idx")
    private int subIdx;

    @Builder
    public Picture(User user, Travel travel, String pictureUrl, int pictureIdx, boolean isFourCut, int subIdx) {
        this.user = user;
        this.travel = travel;
        this.pictureUrl = pictureUrl;
        this.pictureIdx = pictureIdx;
        this.isDeleted=false;
        this.isFourCut=isFourCut;
        this.subIdx=subIdx;
    }
    public static Picture create(String url, int i, Travel travel, User user, boolean isFourCut, int subIdx) {
        return Picture.builder()
                .user(user)
                .travel(travel)
                .pictureUrl(url)
                .pictureIdx(i)
                .isFourCut(isFourCut)
                .subIdx(subIdx)
                .build();
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
