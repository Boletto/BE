package com.demoboletto.domain;

import jakarta.persistence.*;
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
    private int stickerId;

    @Column(name = "field")
    private String field;
}
