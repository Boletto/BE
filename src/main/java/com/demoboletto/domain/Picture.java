package com.demoboletto.domain;

import com.demoboletto.type.ECategory;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "사진")
@NoArgsConstructor
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "travel_id", referencedColumnName = "travel_id")
    private Travel travel;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ECategory category;

    @Column(name = "picture_url")
    private String pictureUrl;
}
