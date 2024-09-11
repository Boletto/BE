package com.demoboletto.domain;

import com.demoboletto.dto.request.CreateTravelDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "travel")
@Getter
@NoArgsConstructor
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private Long travelId;

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
    // need travel owner
    // ++
    @Column(name = "owner")
    private String owner;

    @Builder
    public Travel(String departure, String arrive, String keyword, String startDate, String endDate, String status, String owner) {
        this.departure = departure;
        this.arrive = arrive;
        this.keyword = keyword;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.owner = owner;
    }

    public static Travel create(CreateTravelDto travelDto) {
        return Travel.builder()
                .arrive(travelDto.arrive())
                .departure(travelDto.departure())
                .startDate(travelDto.startDate())
                .endDate(travelDto.endDate())
                .keyword(travelDto.keyword())
                .status(travelDto.status())
                .owner(travelDto.owner())
                .build();
    }
}