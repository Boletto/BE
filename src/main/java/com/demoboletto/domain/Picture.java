package com.demoboletto.domain;

import com.demoboletto.type.ECategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@Table(name = "picture")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id", nullable = true)
    private Travel travel;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ECategory category;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "picture_idx")
    private int pictureIdx;

    @Builder
    public Picture(User user, Travel travel, ECategory category, String pictureUrl, int pictureIdx) {
        this.user = user;
        this.travel = travel;
        this.category = category;
        this.pictureUrl = pictureUrl;
        this.pictureIdx = pictureIdx;
    }
}
