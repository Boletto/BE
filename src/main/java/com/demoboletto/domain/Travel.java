package com.demoboletto.domain;

import com.demoboletto.domain.common.BaseTimeEntity;
import com.demoboletto.dto.request.CreateTravelDto;
import com.demoboletto.dto.request.UpdateTravelDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.type.ETravelStatusType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "travel")
@Getter
@NoArgsConstructor
public class Travel extends BaseTimeEntity {

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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ETravelStatusType status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User editableUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private SysTicket sysTicket;

    @Builder
    public Travel(String departure, String arrive, String keyword, LocalDate startDate, LocalDate endDate, String color, ETravelStatusType status, SysTicket sysTicket) {
        this.departure = departure;
        this.arrive = arrive;
        this.keyword = keyword;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.sysTicket = sysTicket;
    }

    public static Travel create(CreateTravelDto travelDto) {
        return Travel.builder()
                .arrive(travelDto.arrive())
                .departure(travelDto.departure())
                .startDate(travelDto.startDate())
                .endDate(travelDto.endDate())
                .keyword(travelDto.keyword())
                .status(ETravelStatusType.UNLOCK)
                .build();
    }

    public Travel update(UpdateTravelDto travelDto) {
        this.departure = travelDto.departure() != null ? travelDto.departure() : this.departure;
        this.arrive = travelDto.arrive() != null ? travelDto.arrive() : this.arrive;
        this.keyword = travelDto.keyword() != null ? travelDto.keyword() : this.keyword;
        this.startDate = travelDto.startDate() != null ? travelDto.startDate() : this.startDate;
        this.endDate = travelDto.endDate() != null ? travelDto.endDate() : this.endDate;
        return this;
    }

    public void lock(User user) {
        if (this.status == ETravelStatusType.LOCK) {
            throw new CommonException(ErrorCode.TRAVEL_ALREADY_LOCKED);
        }
        this.status = ETravelStatusType.LOCK;
        this.editableUser = user;
    }

    public void unlock() {
        this.status = ETravelStatusType.UNLOCK;
        this.editableUser = null;
    }

    //현재 편집중인 사람과 동일인 인지 확인
    public boolean isEditable(User user) {
        if (this.editableUser == null) {
            this.status = ETravelStatusType.LOCK;
            this.editableUser = user;
            return true;
        }
        return this.editableUser.equals(user);
    }

    public void setSysTicket(SysTicket sysTicket) {
        this.sysTicket = sysTicket;
    }

}