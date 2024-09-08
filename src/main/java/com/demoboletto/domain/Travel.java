package com.demoboletto.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "travel")
@NoArgsConstructor
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private int travelId;

    @Column(name = "departure")
    private String departure;

    @Column(name = "arrive")
    private String arrive;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "status")
    private String status;
}
