package com.demoboletto.domain;

import com.demoboletto.domain.common.Frame;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDate;

@Entity
@Table(name = "sys_frame")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@Getter
public class SysFrame extends Frame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "frame_id")
    private Long frameId;

    @Column(name = "frame_name")
    private String frameName;

    @Column(name = "default_provided", nullable = false)
    private boolean defaultProvided;

    @Column(name = "description")
    private String description;

    @Column(name = "is_event", nullable = false)
    private Boolean isEvent;

    @Column(name = "event_start_date")
    private LocalDate eventStartDate;

    @Column(name = "event_end_date")
    private LocalDate eventEndDate;

    @Builder
    public SysFrame(String frameCode, String frameName, String frameUrl, boolean defaultProvided, String description, LocalDate eventStartDate, LocalDate eventEndDate, Boolean isEvent) {
        this.frameCode = frameCode;
        this.frameName = frameName;
        this.frameUrl = frameUrl;
        this.defaultProvided = defaultProvided;
        this.description = description;
        this.isEvent = isEvent;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
    }


    public boolean isEventExpired() {
        LocalDate now = LocalDate.now();
        return isEvent && eventEndDate.isBefore(now);
    }
}
