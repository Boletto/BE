package com.demoboletto.domain;

import com.demoboletto.dto.request.CreateTravelDto;
import com.demoboletto.dto.request.UpdateTravelDto;
import com.demoboletto.type.EStatusType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


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
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "color")
    private String color;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EStatusType status;

    @Builder
    public Travel(String departure, String arrive, String keyword, LocalDate startDate, LocalDate endDate, String color, EStatusType status) {
        this.departure = departure;
        this.arrive = arrive;
        this.keyword = keyword;
        this.startDate = startDate;
        this.endDate = endDate;
        this.color = color;
        this.status = status;
    }

    public static Travel create(CreateTravelDto travelDto) {
        return Travel.builder()
                .arrive(travelDto.arrive())
                .departure(travelDto.departure())
                .startDate(travelDto.startDate())
                .endDate(travelDto.endDate())
                .keyword(travelDto.keyword())
                .color(travelDto.color())
                .status(EStatusType.UNLOCK)
                .build();
    }

    public Travel update(UpdateTravelDto travelDto) {
        this.departure = travelDto.departure() != null ? travelDto.departure() : this.departure;
        this.arrive = travelDto.arrive() != null ? travelDto.arrive() : this.arrive;
        this.keyword = travelDto.keyword() != null ? travelDto.keyword() : this.keyword;
        this.startDate = travelDto.startDate() != null ? travelDto.startDate() : this.startDate;
        this.endDate = travelDto.endDate() != null ? travelDto.endDate() : this.endDate;
        this.color = travelDto.color() != null ? travelDto.color() : this.color;
        return this;
    }

    public Travel setStatus(EStatusType status) {
        this.status = status;
        return this;
    }
}