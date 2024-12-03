package com.demoboletto.domain;

import com.demoboletto.type.EMemoryType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "travel_memory", uniqueConstraints = {
        @UniqueConstraint(name = "unique_travel_memory_idx", columnNames = {"travel_id", "memory_idx"})
})
public class TravelMemory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memory_id")
    private Long memoryId;

    @Column(name = "frame_code")
    private String frameCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;

    @Column(name = "memory_type")
    @Enumerated(EnumType.STRING)
    private EMemoryType memoryType;

    @Column(name = "memory_idx")
    private Long memoryIdx;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
    @OrderBy("pictureIdx ASC")
    private List<Picture> pictures = new ArrayList<>();

    @Builder
    public TravelMemory(Travel travel, EMemoryType memoryType, Long memoryIdx, String frameCode) {
        this.travel = travel;
        this.memoryType = memoryType;
        this.memoryIdx = memoryIdx;
        this.pictures = new ArrayList<>();
        this.frameCode = frameCode;
    }

    public void attachPictures(List<Picture> pictures) {
        this.pictures.forEach(Picture::detachMemory);
        this.pictures.clear();
        pictures.forEach(picture -> {
            picture.attachMemory(this);
            this.pictures.add(picture);
        });
    }

    public void detachPictures() {
        this.pictures.forEach(Picture::detachMemory);
        this.pictures.clear();
    }

}
