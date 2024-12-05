package com.demoboletto.domain;

import com.demoboletto.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "picture", uniqueConstraints = {
        @UniqueConstraint(name = "unique_memory_id_picture_idx", columnNames = {"memory_id", "picture_idx"})
})
public class Picture extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private Long pictureId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memory_id")
    private TravelMemory travelMemory;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "picture_idx")
    private int pictureIdx;

    @Builder
    public Picture(User user, String pictureUrl, int pictureIdx) {
        this.user = user;
        this.pictureUrl = pictureUrl;
        this.isDeleted = false;
        this.pictureIdx = pictureIdx;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void attachMemory(TravelMemory travelMemory) {
        this.travelMemory = travelMemory;
    }

    public void detachMemory() {
        this.travelMemory = null;
    }

}
